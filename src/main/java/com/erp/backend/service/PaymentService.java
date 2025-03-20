package com.erp.backend.service;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.PaymentDto;
import com.erp.backend.dto.PaymentType;
import com.erp.backend.mapper.MemberMapper;
import com.erp.backend.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final MemberMapper memberMapper;

    //성과급 수정
    public void updateBonus(MemberDto memberDto) {
        paymentMapper.updateBonus(memberDto);
    }

    //급여 내역 저장
    @Transactional
    public void insertPaymentHistory(PaymentDto paymentDto) {
        LocalDate paymentDate = calculatePaymentDate(paymentDto.getYear(), paymentDto.getMonth(), paymentDto.getType());

        Map<String, Object> insertInfo = new HashMap<>();
        insertInfo.put("empId", paymentDto.getEmpId());
        insertInfo.put("paymentDate", paymentDate);
        insertInfo.put("type", paymentDto.getType().toString());

        //기본급, 성과급 저장
        if (paymentDto.getType() == PaymentType.SALARY) {
            Integer baseSalary = memberMapper.getSalaryByEmpId(paymentDto.getEmpId());
            insertInfo.put("amount", baseSalary);
        } else if (paymentDto.getType() == PaymentType.BONUS) {
            Integer departmentBonus = memberMapper.getBonusByEmpId(paymentDto.getEmpId());
            insertInfo.put("amount", departmentBonus);
        }

        paymentMapper.insertPaymentHistory(insertInfo);
    }

    //지급일 계산
    private LocalDate calculatePaymentDate(int year, int month, PaymentType type) {
        return (type == PaymentType.BONUS) ? LocalDate.of(year, 3, 22) : LocalDate.of(year, month, 25);
    }
}