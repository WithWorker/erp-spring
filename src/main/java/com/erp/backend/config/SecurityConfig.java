package com.erp.backend.config;

import com.erp.backend.dto.MemberRole;
import com.erp.backend.security.filter.AuthenticationFilter;
import com.erp.backend.security.filter.CustomLogoutFilter;
import com.erp.backend.security.filter.JwtFilter;
import com.erp.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService empDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(empDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter(authManager, jwtUtil);
        authFilter.setFilterProcessesUrl("/login");

        http.csrf((auth) -> auth.disable())
                .cors(cors -> cors.configurationSource(corsSource()))
                .formLogin((auth) -> auth.disable())
                .httpBasic((auth) -> auth.disable())
                //필터 인가
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/**").hasAnyRole(MemberRole.USER.name(), MemberRole.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole(MemberRole.ADMIN.name())
                        .anyRequest().permitAll()
                        )
                //필터 추가
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new CustomLogoutFilter(), LogoutFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.setAllowedOriginPatterns(List.of("*"));
        corsConfig.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
}
