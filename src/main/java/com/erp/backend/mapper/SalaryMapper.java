package com.erp.backend.mapper;

import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface SalaryMapper {

    //기본급 수정
    void updateSalary(MemberDto memberDto);

    //성과급 수정
    void updateBonus(MemberDto memberDto);

    //기본급 내역 저장
    void insertSalaryHistory(LocalDate paymentDate);

    //성과급 내역 저장
    void insertBonusHistory(LocalDate paymentDate);
}
