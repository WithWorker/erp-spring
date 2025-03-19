package com.erp.backend.controller;

import com.erp.backend.model.AlarmVO;
import com.erp.backend.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    // 테스트용 API
    @GetMapping("")
    public String alarmTest() {
        return "test";
    }

    // 알람 추가 (로그인 체크 제거)
    @PostMapping("/add")
    public String addAlarm(@RequestBody Map<String, String> alarmData) {
        alarmService.addAlarm(alarmData);
        return "알림이 추가되었습니다.";
    }

    // 모든 알람 조회 (로그인 체크 제거)
    @GetMapping("/list")
    public List<AlarmVO> listAlarm(@RequestParam Long empId) {
        return alarmService.getAlarmList(empId);
    }

    // 지난 알람 조회 (최근 30일 알림)(로그인 체크 제거)
    @GetMapping("/list/past")
    public List<AlarmVO> listPastAlarm(@RequestParam Long empId) {
        return alarmService.getPastAlarmList(empId);
    }

    // 특정 알람 읽기 (로그인 체크 제거)
    @PostMapping("/read")
    public String readAlarm(@RequestBody Map<String, Long> alarmData) {
        Long alarmId = alarmData.get("alarmId");
        Long empId = alarmData.get("empId");
        boolean success = alarmService.readAlarm(alarmId, empId);
        return success ? "알림 읽기 성공" : "알림 읽기 실패 (권한 없음)";
    }

    // 모든 알람 읽기 (로그인 체크 제거)
    @PostMapping("/read/all")
    public String readAllAlarm(@RequestParam Long empId) {
        alarmService.readAllAlarm(empId);
        return "알람을 모두 읽음 처리했습니다!";
    }

    // 안 읽은 알람 개수 조회 (로그인 체크 제거)
    @GetMapping("/read/not")
    public String readNotAlarm(@RequestParam Long empId) {
        return alarmService.getUnreadAlarm(empId);
    }
}