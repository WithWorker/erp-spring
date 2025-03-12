package com.erp.backend.security;

import com.erp.backend.dto.MemberRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    private final long tokenExpiration;

    public JwtUtil(@Value("${spring.jwt.secret}") String secretKey,
                   @Value("${spring.jwt.expiration}") long tokenExpiration) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.tokenExpiration = tokenExpiration;
    }

    //토큰 생성
    public String createToken(Long empId, String email, String role) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + tokenExpiration))
                .claim("email", email)
                .claim("role", role)
                .claim("empId", empId)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getEmpId(String token) {
        return parseClaims(token).get("empId", Long.class);
    }

    public String getEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    public MemberRole getRole(String token) {
        String roleStr = parseClaims(token).get("role", String.class);
        return MemberRole.valueOf(roleStr);
    }

    public List<SimpleGrantedAuthority> getAuthorities(MemberRole role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    //유효성 검사
    public boolean validateToken(String token) {
        try {
            return !parseClaims(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
