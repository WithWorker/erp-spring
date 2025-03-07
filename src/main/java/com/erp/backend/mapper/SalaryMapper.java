package com.erp.backend.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface SalaryMapper {

    //기본급 내역 저장
    void insertSalaryHistory(LocalDate paymentDate);

    //성과급 내역 저장
    void insertBonusHistory(LocalDate paymentDate);
}
