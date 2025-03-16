package com.erp.backend.mapper;

import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
    void updatePosition(Long empId, Long positionId, Integer newSalary);

    //퇴사
    void resignMember(Long empId);

    //비밀번호 재설정
    void updatePassword(MemberDto member);

    //직원삭제
    void deleteMember(Long empId);

    //직급별 급여 조회
    Integer getSalaryByPositionId(Long positionId);

    //부서별 성과급 조회
    Integer getDepartmentBonusByDepartmentId(Long departmentId);

    //직원 ID로 기본급 조회
    Integer getBaseSalaryByEmpId(Long empId);

    //직원 ID로 직급 ID 조회
    Integer getPositionIdByEmpId(Long empId);

    //직원 ID로 부서 ID 조회
    Integer getDepartmentIdByEmpId(Long empId);

    //기본급 업데이트
    void updateBaseSalary(Long empId, Integer newSalary);
}