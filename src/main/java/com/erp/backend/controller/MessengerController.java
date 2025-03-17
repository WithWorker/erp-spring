package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.model.FileVO;
import com.erp.backend.model.MessengerVO;
import com.erp.backend.service.MessengerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messenger")
public class MessengerController {

    @Autowired
    private MessengerService ms;

    /*
    // 세션 기반 로그인 사용자 ID 반환 ==> JWT 기반으로 변경할 것!!
    private Long getLoginEmpId(HttpSession session) {
        MemberDto user = (MemberDto) session.getAttribute("user");
        if(user == null) {
            throw new RuntimeException("로그인이 필요합니다");
        }
        return user.getEmpId();
    }
    */

    // Test
    @GetMapping("")
    public String messengertest() {
        return "test";
    }

    // 전체 부서 조회
    @GetMapping("/dept")
    public List<Map<String, String>> getDept() {
        return ms.getDept();
    }

    // 부서 직원 조회
    @GetMapping("/dept/person")
    public List<Map<String, String>> getDeptPerson(@RequestParam Long deptId) {
        return ms.getDeptPerson(Map.of("deptId", String.valueOf(deptId)));
    }

    // 해당 부서의 팀 조회
    @GetMapping("/dept/team")
    public List<Map<String, String>> getDeptTeam(@RequestParam Long deptId) {
        return ms.getTeam(String.valueOf(deptId));
    }

    // 메시지 리스트 조회 (보낸 메시지 + 받은 메시지)
    @GetMapping("/message/list")
    public Map<String, List<Map<String, Object>>> getMessageList(@RequestParam Long empId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("senderId", empId);  //
        paramMap.put("receiverId", empId);

        // 보낸 메시지 조회
        List<Map<String, Object>> sendMsg = ms.getSendMsg(paramMap);
        // 받은 메시지 조회
        List<Map<String, Object>> receiveMsg = ms.getReceivedMsg(paramMap);

        return Map.of("sendMsg", sendMsg, "receiveMsg", receiveMsg);
    }

    // 보낸 메시지 내용 조회
    @GetMapping("/message/content/send")
    public Map<String, Object> getMsgContent(@RequestParam Long msgId, @RequestParam Long senderId) {
        return ms.getMsgContent(msgId, senderId);
    }

    // 받은 메시지 내용 조회
    @GetMapping("/message/content/receive")
    public Map<String, Object> getMsgContent2(@RequestParam Long msgId, @RequestParam Long receiverId) {
        return ms.getMsgContent2(msgId, receiverId);
    }

    // 메시지 읽음 처리
    @PostMapping("/message/read")
    public ResponseEntity<?> readAllMessages(@RequestParam(required = false) Long empId) {
        if (empId == null) {
            return ResponseEntity.badRequest().body("❌ empId가 필요합니다.");
        }
        ms.updateAllMsg(empId);
        return ResponseEntity.ok("✅ 모든 메시지가 읽음 처리되었습니다.");
    }


    // 첨부파일 추가
    @PostMapping("/file/add")
    public String addFile(@RequestBody FileVO fvo) {
        ms.addFile(fvo);
        return "첨부파일이 추가되었습니다.";
    }

    // 첨부파일 조회
    @GetMapping("/file/list")
    public List<FileVO> getFileList(@RequestParam Long msgId) {
        return ms.getMsgFile(msgId);
    }

    // 메신저 전달
    @PostMapping("/deliver")
    public String deliverMessage(@RequestBody Map<String, MessengerVO> msg, @RequestParam Long empId) {
        MessengerVO mvo = msg.get("mvo");
        MessengerVO mvo1 = msg.get("mvo1");
        mvo.setSenderId(empId);
        ms.deliverMessage(mvo, mvo1);
        return "메시지 전달 성공";
    }

    // 메신저 방 개수
    @GetMapping("/room/count")
    public int getRoomCount(@RequestParam Long empId) {
        return ms.getTotalMsg(Map.of("empId", String.valueOf(empId)));
    }

    // 메시지 발송을 위한 사람 이름 조회
    @GetMapping("/send/name")
    public Map<String, Object> getEmpName(@RequestParam Long receiverId) {
        return ms.getEmpName(receiverId);
    }

    // 안 읽은 메시지 개수 조회
    @GetMapping("/message/unread/count")
    public ResponseEntity<?> getUnreadMsg(@RequestParam(required = false) Long empId) {
        if (empId == null) {
            return ResponseEntity.badRequest().body("❌ empId가 필요합니다.");
        }
        int unreadCount = ms.getUnreadMsg(empId);
        return ResponseEntity.ok(Map.of("unreadCount", unreadCount));
    }
}
