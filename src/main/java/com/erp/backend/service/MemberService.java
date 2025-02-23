package com.erp.backend.service;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.MemberRole;
import com.erp.backend.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    //사원등록
    public void insertMember(MemberDto memberDto) {
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPassword);
        memberDto.setMemberRole(MemberRole.USER);
        memberMapper.insertMember(memberDto);
    }

}
