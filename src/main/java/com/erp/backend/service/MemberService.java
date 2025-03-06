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

    //직원조회 (by 이름)
    public MemberDto findByName(String name){
        return memberMapper.findByName(name);
    }

    //직원조회 (by 직원id)
    public MemberDto findById(Long empId) {
        return memberMapper.findById(empId);
    }

    //직원조회 (by 부서id)
    public List<MemberDto> findAllByDept(Long departmentId) {
        return memberMapper.findAllByDept(departmentId);
    }

    //직원등록
    public void insertMember(MemberDto memberDto) {
        if (memberMapper.findByEmail(memberDto.getEmail()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(encodedPassword);
        memberDto.setMemberRole(MemberRole.USER);

        memberMapper.insertMember(memberDto);
    }

    //직원수정
    public void updateMember(MemberDto memberDto) {
        memberMapper.updateMember(memberDto);
    }

    //부서이동
    public void updateDepartment(MemberDto memberDto) {
        memberMapper.updateDepartment(memberDto);
    }

    //직급변경
    public void updatePosition(MemberDto memberDto) {
        memberMapper.updatePosition(memberDto);
    }

    //퇴사
    public void resignMember(Long empId) {
        memberMapper.resignMember(empId);
    }

    //비밀번호 재설정
    public void updatePassword(MemberDto memberDto) {
        MemberDto member = memberMapper.findByPhone(memberDto.getPhone());

        if (member == null) {
            throw new IllegalArgumentException("해당 번호로 등록된 사원을 찾을 수 없습니다.");
        }

        if (!memberDto.getPassword().equals(memberDto.getRepassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(encodedPassword);
        memberMapper.updatePassword(member);
    }

    //직원삭제
    public void deleteMember(Long empId) {
        memberMapper.deleteMember(empId);
    }

}