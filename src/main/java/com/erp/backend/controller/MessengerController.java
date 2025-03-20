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
import org.springframework.web.server.ResponseStatusException;

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
    public List<Map<String, Object>> getDept() {
        return ms.getDept();
    }

    // 특정 부서 내 직원 조회
    @GetMapping("/dept/person")
    public List<Map<String, Object>> getDeptPerson(@RequestParam Long deptId) {
        System.out.println("🔍 부서 직원 조회 요청: deptId=" + deptId);
        return ms.getDeptPerson(Map.of("deptId", String.valueOf(deptId)));
    }

    // 선택 직원 조회
    @GetMapping("/dept/person/chosen")
    public List<Map<String, Object>> getChosenEmp(@RequestParam Long empId) {
        System.out.println("🔍 선택된 직원 조회 요청: empId=" + empId);
        return ms.getChosenEmp(empId);
    }

    // 메시지 전송
    @PostMapping("/message/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessengerVO msgvo) {
        ms.sendMessage(msgvo);
        return ResponseEntity.ok("✅ 메시지가 성공적으로 전송되었습니다. msgId=" + msgvo.getMessengerId());
    }

    // 메시지 전달 기능
    @PostMapping("/message/deliver")
    public ResponseEntity<?> deliverMessage(@RequestBody Map<String, Object> requestData) {
        Long msgId = ((Number) requestData.get("msgId")).longValue(); // 기존 메시지 ID
        Long senderId = ((Number) requestData.get("senderId")).longValue();
        Long receiverId = ((Number) requestData.get("receiverId")).longValue();

        // 기존 메시지 가져오기
        MessengerVO originalMessage = ms.getMessageById(msgId);
        if (originalMessage == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ 전달할 메시지가 존재하지 않습니다.");
        }

        // 새로운 메시지 객체 생성 (기존 메시지 내용 유지)
        MessengerVO newMessage = new MessengerVO();
        newMessage.setSenderId(senderId);
        newMessage.setReceiverId(receiverId);
        newMessage.setContent(originalMessage.getContent()); // 원본 메시지 내용 유지
        newMessage.setFilePath(originalMessage.getFilePath()); // 기존 파일 유지

        ms.deliverMessage(originalMessage, newMessage);
        return ResponseEntity.ok("✅ 메시지 전달 완료!");
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
    public ResponseEntity<String> addFile(@RequestBody FileVO filevo) {
        ms.addFile(filevo);
        return ResponseEntity.ok("✅ 첨부파일이 추가되었습니다.");
    }

    // 첨부파일 조회
    @GetMapping("/file/list")
    public ResponseEntity<List<FileVO>> getMsgFiles(@RequestParam Long msgId) {
        List<FileVO> files = ms.getMsgFile(msgId);
        if (files == null || files.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(files);
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

    // 메신저 방 삭제
    @DeleteMapping("/message/delete")
    public ResponseEntity<String> deleteMessage(@RequestParam(required = true) Long msgId,
                                                @RequestParam(required = true) Long empId) {
        System.out.println("🔍 삭제 요청: msgId=" + msgId + ", empId=" + empId);

        boolean isDeleted = ms.deleteMessage(msgId, empId);

        if (isDeleted) {
            return ResponseEntity.ok("메시지가 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 메시지를 찾을 수 없습니다.");
        }
    }
}
