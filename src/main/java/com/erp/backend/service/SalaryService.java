package com.erp.backend.service;

import com.erp.backend.mapper.SalaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SalaryService {

    private final SalaryMapper salaryMapper;

    public void saveSalaryHistory(LocalDate paymentDate) {
        salaryMapper.insertSalaryHistory(paymentDate);
    }

    public void saveBonusHistory(LocalDate paymentDate) {
        salaryMapper.insertBonusHistory(paymentDate);
    }
}
