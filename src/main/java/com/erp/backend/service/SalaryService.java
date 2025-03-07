package com.erp.backend.service;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.mapper.SalaryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SalaryService {
    private final SalaryMapper salaryMapper;

    //기본급 수정
    public void updateSalary(MemberDto memberDto) {
        salaryMapper.updateSalary(memberDto);
    }

    //성과급 수정
    public void updateBonus(MemberDto memberDto) {
        salaryMapper.updateBonus(memberDto);
    }

    //기본급 내역 저장
    public void saveSalaryHistory(LocalDate paymentDate) {
        salaryMapper.insertSalaryHistory(paymentDate);
    }

    //성과급 내역 저장
    public void saveBonusHistory(LocalDate paymentDate) {
        salaryMapper.insertBonusHistory(paymentDate);
    }
}
