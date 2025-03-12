package com.erp.backend.mapper;

import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    //전체조회
    List<MemberDto> findAll();

    //전체조회 (by 부서id)
    List<MemberDto> findAllByDept(Long departmentId);

    //직원조회 (by 이름)
    List<MemberDto> findByName(String name);

    //직원조회 (by 직원id)
    MemberDto findById(Long empId);

    //직원조회 (by 이메일)
    MemberDto findByEmail(String email);

    //직원조회 (by phone)
    MemberDto findByPhone(String phone);

    //직원등록
    void insertMember(MemberDto memberDto);

    //직원수정
    void updateMember(MemberDto memberDto);

    //부서이동
    void updateDepartment(MemberDto memberDto);

    //직급변경
    void updatePosition(MemberDto memberDto);

    //퇴사
    void resignMember(Long empId);

    //비밀번호 재설정
    void updatePassword(MemberDto member);

    //직원삭제
    void deleteMember(Long empId);
}