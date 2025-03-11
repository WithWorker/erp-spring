package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.model.AlarmVO;
import com.erp.backend.service.AlarmService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

    /*
    AlarmController
    - @RestController 사용
    - RESTful API
    - JWT, OAuth 토큰 기반 인증
    - 로그인 상태에서만 가능
     */

    @Autowired
    private AlarmService alarmService;

    // AOP 알람 추가
    @PostMapping("/add")
    public String addAlarm(@RequestBody Map<String, String> alarmData, HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("member");
        if(member == null) {
            return "로그인이 필요합니다.";
        }
        alarmData.put("receiverId", String.valueOf(member.getEmpId())); // empId(Long) -> String 변환
        alarmService.addAlarm(alarmData);
        return "알림이 추가되었습니다";
    }

    // 사용자의 알람 조회
    @GetMapping("/list")
    public List<AlarmVO> listAlarm(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("member");
        if(member == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        return alarmService.getAlarmList(member.getEmpId());
    }

    // 지난 알람 조회
    @GetMapping("/list/past")
    public List<AlarmVO> listPastAlarm(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("member");
        if(member == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        return alarmService.getPastAlarmList(member.getEmpId());
    }

    // 알람 읽기
    @PostMapping("/read")
    public String readAlarm(@RequestBody Map<String, Long> alarmData, HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("member");
        if(member == null) {
            return "로그인이 필요합니다.";
        }
            Long alarmId = alarmData.get("alarmId");
            boolean success = alarmService.readAlarm(alarmId, member.getEmpId());
            return success ? "알림 읽기 성공" : "알림 읽기 실패 (권한 없음)";
    }

    // 모든 알람 읽기
    @PostMapping("/read/all")
    public String readAllAlarm(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("member");
        if(member == null) {
            return "로그인이 필요합니다.";
        }
        alarmService.readAllAlarm(member.getEmpId());
        return "알람을 모두 읽음 처리했습니다!";
    }

    // 안읽은 알람 개수
    @GetMapping("/read/not")
    public String readNotAlarm(HttpSession session) {
        MemberDto member = (MemberDto) session.getAttribute("member");
        if(member == null) {
            return "로그인이 필요합니다.";
        }
        return alarmService.getUnreadAlarm(member.getEmpId());
    }
}
