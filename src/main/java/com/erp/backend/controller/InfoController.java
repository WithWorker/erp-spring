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
    @PostMapping("/user/profile")
    public MemberDto profile(@RequestBody MemberDto memberDto) {
        return infoService.profile(memberDto);
    }

    //급여 조회
    @PostMapping("/user/paymentHistory/{empId}")
    public List<PaymentDto> getPaymentHistory(
            @PathVariable Long empId,
            @RequestBody Map<String, Integer> request) {
        int year = request.get("year");
        int month = request.get("month");
        return infoService.getPaymentHistory(empId, year, month);
    }

    //근태 조회
    @PostMapping("/user/attendance/{empId}")
    public List<AttendanceDto> getMonthlyAttendance(
            @PathVariable("empId") Long empId,
            @RequestBody Map<String, String> requestBody) {

        // JSON에서 year, month 추출
        String year = requestBody.get("year");
        String month = requestBody.get("month");

        if (year == null || month == null) {
            throw new IllegalArgumentException("연도와 월 정보가 필요합니다.");
        }

        // YYYY-MM 형식으로 변환
        String yearMonth = year + "-" + month;

        return infoService.getMonthlyAttendance(empId, yearMonth);
    }

    //출근 기록
    @PostMapping("/user/attendance/in/{empId}")
    public String checkIn(@PathVariable(value = "empId") Long empId) {
        infoService.checkIn(empId);
        return "출근 성공";
    }

    //퇴근 기록
    @PutMapping("/user/attendance/out/{empId}")
    public String checkOut(@PathVariable(value = "empId") Long empId) {
        infoService.checkOut(empId);
        return "퇴근 성공";
    }
}

