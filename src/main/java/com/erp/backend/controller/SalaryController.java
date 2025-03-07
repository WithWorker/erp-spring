package com.erp.backend.controller;

import com.erp.backend.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    //기본급 내역 저장
    @PostMapping("/saveSalary")
    public ResponseEntity<String> saveSalaryHistory() {
        LocalDate now = LocalDate.now().withDayOfMonth(1);
        salaryService.saveSalaryHistory(now);
        return ResponseEntity.ok("기본급 내역이 저장되었습니다. : " + now);
    }

    //성과급 내역 저장
    @PostMapping("/saveBonus")
    public ResponseEntity<String> saveBonusHistory() {
        LocalDate now = LocalDate.now().withMonth(3).withDayOfMonth(5);
        salaryService.saveBonusHistory(now);
        return ResponseEntity.ok("성과급 내역이 저장되었습니다. : " + now);
    }
}

