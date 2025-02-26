package com.erp.backend.service;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.MemberRole;
import com.erp.backend.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    //전체조회
    public List<MemberDto> findAll(){
        return memberMapper.findAll();
    }

    //사원조회 (by 이름)
    public MemberDto findByName(String name){
        return memberMapper.findByName(name);
    }

    //사원조회 (by id)
    public MemberDto findById(Long empId) {
        return memberMapper.findById(empId);
    }

    //사원등록
    public void insertMember(MemberDto memberDto) {
        if (memberMapper.findByEmail(memberDto.getEmail()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPassword);
        memberDto.setMemberRole(MemberRole.USER);

        memberMapper.insertMember(memberDto);
    }

    //사원수정
    public void updateMember(MemberDto memberDto) {
        memberMapper.updateMember(memberDto);
    }

    //사원삭제
    public void deleteMember(Long empId) {
        memberMapper.deleteMember(empId);
    }

}