package com.erp.backend.security;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class EmpDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberDto member = memberMapper.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                "ROLE_" + member.getMemberRole().toString()
        );

        return new CustomUserDetails(
                member.getEmpId(),
                member.getEmail(),
                member.getPassword(),
                Collections.singleton(authority)
        );
    }
}
