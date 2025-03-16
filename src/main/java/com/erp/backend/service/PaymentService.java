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

    //기본급 수정
    public void updateSalary(MemberDto memberDto) {
        paymentMapper.updateSalary(memberDto);
    }

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

        if (paymentDto.getType() == PaymentType.SALARY) {
            Integer salaryAmount = paymentDto.getAmount();

            //base_salary 조회
            if (salaryAmount == null) {
                salaryAmount = memberMapper.getBaseSalaryByEmpId(paymentDto.getEmpId());

                if (salaryAmount == null) {
                    Integer positionId = memberMapper.getPositionIdByEmpId(paymentDto.getEmpId());
                    if (positionId != null) {
                        salaryAmount = memberMapper.getSalaryByPositionId(positionId.longValue());
                    }
                }
            }

            insertInfo.put("amount", salaryAmount);

            //급여 내역 저장
            paymentMapper.insertPaymentHistory(insertInfo);

            //base_salary 업데이트
            if (salaryAmount != null) {
                memberMapper.updateBaseSalary(paymentDto.getEmpId(), salaryAmount);
            }

        } else if (paymentDto.getType() == PaymentType.BONUS) {
            Integer departmentId = memberMapper.getDepartmentIdByEmpId(paymentDto.getEmpId());
            if (departmentId != null) {
                Integer bonusAmount = memberMapper.getDepartmentBonusByDepartmentId(departmentId.longValue());
                insertInfo.put("amount", bonusAmount);

                //성과급 내역 저장
                paymentMapper.insertPaymentHistory(insertInfo);
            }
        }
    }

    //지급일 계산
    private LocalDate calculatePaymentDate(int year, int month, PaymentType type) {
        if (type == PaymentType.BONUS) {
            return LocalDate.of(year, 3, 22); //성과급
        }
        return LocalDate.of(year, month, 25); //기본급
    }
}
