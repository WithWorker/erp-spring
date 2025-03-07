package com.erp.backend.controller;

import com.erp.backend.dto.AttendanceDto;
import com.erp.backend.dto.MemberDto;
import com.erp.backend.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    //프로필 조회
    @GetMapping("/info/{empId}")
    public MemberDto profile(@PathVariable(value = "empId") Long empId) {
        return infoService.profile(empId);
    }

    //근태 조회
    @GetMapping("/info/attendance/{empId}")
    public AttendanceDto attendance(@PathVariable(value = "empId") Long empId) {
        return infoService.attendance(empId);
    }

    //출근 기록
    @PostMapping("/info/attendance/in/{empId}")
    public String checkIn(@PathVariable(value = "empId") Long empId) {
        infoService.checkIn(empId);
        return "출근 시간이 기록되었습니다.";
    }

    //퇴근 기록
    @PutMapping("/info/attendance/out/{empId}")
    public String checkOut(@PathVariable(value = "empId") Long empId) {
        infoService.checkOut(empId);
        return "퇴근 시간이 기록되었습니다.";
    }
}

