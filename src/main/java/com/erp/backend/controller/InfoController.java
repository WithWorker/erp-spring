package com.erp.backend.controller;

import com.erp.backend.dto.AttendanceDto;
import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.PaymentDto;
import com.erp.backend.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    //프로필 조회
    @GetMapping("/profile/{empId}")
    public MemberDto profile(@PathVariable(value = "empId") Long empId) {
        return infoService.profile(empId);
    }

    //급여 조회
    @PostMapping("/paymentHistory/{empId}")
    public List<PaymentDto> getPaymentHistory(
            @PathVariable Long empId,
            @RequestBody Map<String, Integer> request) {
        int year = request.get("year");
        int month = request.get("month");
        return infoService.getPaymentHistory(empId, year, month);
    }

    //근태 조회
    @GetMapping("/attendance/{empId}")
    public AttendanceDto attendance(@PathVariable(value = "empId") Long empId) {
        return infoService.attendance(empId);
    }

    //출근 기록
    @PostMapping("/attendance/in/{empId}")
    public String checkIn(@PathVariable(value = "empId") Long empId) {
        infoService.checkIn(empId);
        return "출근 성공";
    }

    //퇴근 기록
    @PutMapping("/attendance/out/{empId}")
    public String checkOut(@PathVariable(value = "empId") Long empId) {
        infoService.checkOut(empId);
        return "퇴근 성공";
    }
}

