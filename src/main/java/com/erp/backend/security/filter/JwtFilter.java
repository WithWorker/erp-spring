package com.erp.backend.security.filter;

import com.erp.backend.security.CustomUserDetails;
import com.erp.backend.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        //유효성 검사
        if (token != null && jwtUtil.validateToken(token)) {
            Authentication authentication = createAuth(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    //토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Authentication createAuth(String token) {
        Long empId = jwtUtil.getEmpId(token);
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);
        List<SimpleGrantedAuthority> auth = jwtUtil.getAuthorities(role);

        CustomUserDetails principal = new CustomUserDetails(empId, email, "", auth);
        return new UsernamePasswordAuthenticationToken(
                principal, null, principal.getAuthorities()
        );
    }
}
