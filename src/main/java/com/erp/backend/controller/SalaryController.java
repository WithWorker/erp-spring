package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.service.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    //기본급 수정
    @PutMapping("/updateSalary/{positionId}")
    public String updateSalary(@PathVariable Long positionId, @RequestBody MemberDto memberDto) {
        memberDto.setPositionId(positionId);
        salaryService.updateSalary(memberDto);
        return "success";
    }

    //성과급 수정
    @PutMapping("/updateBonus/{departmentId}")
    public String updateBonus(@PathVariable Long departmentId, @RequestBody MemberDto memberDto) {
        memberDto.setDepartmentId(departmentId);
        salaryService.updateBonus(memberDto);
        return "success";
    }

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

