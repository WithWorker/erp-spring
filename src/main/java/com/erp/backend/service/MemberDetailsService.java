package com.erp.backend.service;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberDto member = memberMapper.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return User.withUsername(member.getEmail())
                .password(member.getPassword())
                .roles(member.getMemberRole().toString())
                .build();
    }
}
