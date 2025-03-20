package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.PaymentDto;
import com.erp.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    //성과급 수정
    @PutMapping("/updateBonus/{departmentId}")
    public String updateBonus(@PathVariable Long departmentId, @RequestBody MemberDto memberDto) {
        memberDto.setDepartmentId(departmentId);
        paymentService.updateBonus(memberDto);
        return "success";
    }

    //급여내역 저장
    @PostMapping("/savePayment/{empId}")
    public ResponseEntity<String> insertPaymentHistory(@PathVariable Long empId, @RequestBody PaymentDto paymentDto) {
        paymentDto.setEmpId(empId);
        paymentService.insertPaymentHistory(paymentDto);
        return ResponseEntity.ok("급여내역이 저장되었습니다.");
    }
}

