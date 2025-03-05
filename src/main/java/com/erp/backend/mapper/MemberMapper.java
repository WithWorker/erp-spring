package com.erp.backend.mapper;

import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    //전체조회
    List<MemberDto> findAll();

    //사원조회 (by 이름)
    MemberDto findByName(String name);

    //사원조회 (by id)
    MemberDto findById(Long empId);

    //사원조회 (by 이메일)
    MemberDto findByEmail(String email);

    //사원조회 (by phone)
    MemberDto findByPhone(String phone);

    //사원등록
    void insertMember(MemberDto memberDto);

    //사원수정
    void updateMember(MemberDto memberDto);

    //부서이동
    void updateDepartment(MemberDto memberDto);

    //직급변경
    void updatePosition(MemberDto memberDto);

    //퇴사
    void resignMember(Long empId);

    //비밀번호 재설정
    void updatePassword(MemberDto member);

    //사원삭제
    void deleteMember(Long empId);
}