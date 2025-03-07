package com.erp.backend.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface SalaryMapper {

    void insertSalaryHistory(LocalDate paymentDate);

    void insertBonusHistory(LocalDate paymentDate);
}
