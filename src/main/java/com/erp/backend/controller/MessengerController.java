package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.GroupMessageDTO;
import com.erp.backend.model.FileVO;
import com.erp.backend.model.MessengerVO;
import com.erp.backend.service.MessengerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/kkh/messenger")
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
    public ResponseEntity<Map<String, Object>> messengertest() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "test");
        return ResponseEntity.ok(result);
    }

    // 전체 부서 조회
    @GetMapping("/dept")
    public ResponseEntity<List<Map<String, Object>>> getDept() {
        return ResponseEntity.ok(ms.getDept());
    }

    // 특정 부서 내 직원 조회
    @GetMapping("/dept/person")
    public ResponseEntity<List<Map<String, Object>>> getDeptPerson(@RequestParam Long deptId) {
        System.out.println("🔍 부서 직원 조회 요청: deptId=" + deptId);
        return ResponseEntity.ok(ms.getDeptPerson(Map.of("deptId", String.valueOf(deptId))));
    }

    // 선택 직원 조회
    @GetMapping("/dept/person/chosen")
    public ResponseEntity<List<Map<String, Object>>> getChosenEmp(@RequestParam Long empId) {
        System.out.println("🔍 선택된 직원 조회 요청: empId=" + empId);
        return ResponseEntity.ok(ms.getChosenEmp(empId));
    }

    // 메시지 전송 (1:1 포함)
    @PostMapping("/message/send")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody MessengerVO msgvo) {
        ms.sendMessage(msgvo);
        Map<String, Object> result = new HashMap<>();
        result.put("message", "✅ 메시지가 성공적으로 전송되었습니다.");
        result.put("msgId", msgvo.getMessengerId());
        return ResponseEntity.ok(result);
    }

    // 단체 메시지 전송 (GroupMessageDTO 사용)
    @PostMapping("/message/sendGroup")
    public ResponseEntity<Map<String, Object>> sendGroupMessage(@RequestBody GroupMessageDTO groupMsg) {
        if (groupMsg.getSenderId() == null ||
                groupMsg.getReceiverIds() == null ||
                groupMsg.getReceiverIds().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "❌ senderId 또는 receiverIds가 필요합니다.");
        }
        int roomId = ms.sendGroupMessage(groupMsg);
        Map<String, Object> result = new HashMap<>();
        result.put("message", "✅ 단체 메시지가 성공적으로 전송되었습니다.");
        result.put("roomId", roomId);
        result.put("messengerId", groupMsg.getMessengerId());

        return ResponseEntity.ok(result);
    }

    // 메시지 전달 기능
    @PostMapping("/message/deliver")
    public ResponseEntity<Map<String, Object>> deliverMessage(@RequestBody Map<String, Object> requestData) {
        Long msgId = ((Number) requestData.get("msgId")).longValue(); // 기존 메시지 ID
        Long senderId = ((Number) requestData.get("senderId")).longValue();
        Long receiverId = ((Number) requestData.get("receiverId")).longValue();

        // 기존 메시지 가져오기
        MessengerVO originalMessage = ms.getMessageById(msgId);
        if (originalMessage == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "❌ 전달할 메시지가 존재하지 않습니다.");
        }

        // 새로운 메시지 객체 생성 (기존 메시지 내용 유지)
        MessengerVO newMessage = new MessengerVO();
        newMessage.setSenderId(senderId);
        newMessage.setReceiverId(receiverId);
        newMessage.setContent(originalMessage.getContent());
        newMessage.setFilePath(originalMessage.getFilePath());

        ms.deliverMessage(originalMessage, newMessage);
        Map<String, Object> result = new HashMap<>();
        result.put("message", "✅ 메시지 전달 완료!");
        return ResponseEntity.ok(result);
    }

    // 메시지 리스트 조회 (보낸 메시지 + 받은 메시지)
    @GetMapping("/message/list")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getMessageList(@RequestParam Long empId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("senderId", empId);
        paramMap.put("receiverId", empId);

        List<Map<String, Object>> sendMsg = ms.getSendMsg(paramMap);
        List<Map<String, Object>> receiveMsg = ms.getReceivedMsg(paramMap);

        Map<String, List<Map<String, Object>>> result = Map.of("sendMsg", sendMsg, "receiveMsg", receiveMsg);
        return ResponseEntity.ok(result);
    }

    // 보낸 메시지 내용 조회
    @GetMapping("/message/content/send")
    public ResponseEntity<Map<String, Object>> getMsgContent(@RequestParam Long msgId, @RequestParam Long senderId) {
        return ResponseEntity.ok(ms.getMsgContent(msgId, senderId));
    }

    // 받은 메시지 내용 조회
    @GetMapping("/message/content/receive")
    public ResponseEntity<Map<String, Object>> getMsgContent2(@RequestParam Long msgId, @RequestParam Long receiverId) {
        return ResponseEntity.ok(ms.getMsgContent2(msgId, receiverId));
    }

    // 메시지 읽음 처리
    @PostMapping("/message/read")
    public ResponseEntity<Map<String, Object>> readAllMessages(@RequestParam(required = false) Long empId) {
        if (empId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "❌ empId가 필요합니다.");
        }
        ms.updateAllMsg(empId);
        Map<String, Object> result = new HashMap<>();
        result.put("message", "✅ 모든 메시지가 읽음 처리되었습니다.");
        return ResponseEntity.ok(result);
    }

    // 첨부파일 추가
    @PostMapping("/file/add")
    public ResponseEntity<Map<String,Object>> addFile(@RequestBody FileVO filevo) {
        // filevo.getMessengerId()가 0이면 실패
        if(filevo.getMessengerId() == null || filevo.getMessengerId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 메시지ID");
        }
        ms.addFile(filevo);
        Map<String,Object> result = new HashMap<>();
        result.put("message", "✅ 첨부파일이 추가되었습니다.");
        return ResponseEntity.ok(result);
    }


    // 첨부파일 조회
    @GetMapping("/file/list")
    public ResponseEntity<List<FileVO>> getMsgFiles(@RequestParam Long msgId) {
        List<FileVO> files = ms.getMsgFile(msgId);
        if (files == null || files.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(files);
    }

    // 메신저 방 개수 조회 (대화방 단위)
    @GetMapping("/room/count")
    public ResponseEntity<Integer> getRoomCount(@RequestParam Long empId) {
        int count = ms.getTotalMsg(Map.of("empId", String.valueOf(empId)));
        return ResponseEntity.ok(count);
    }

    // 메시지 발송을 위한 사람 이름 조회
    @GetMapping("/send/name")
    public ResponseEntity<Map<String, Object>> getEmpName(@RequestParam Long receiverId) {
        return ResponseEntity.ok(ms.getEmpName(receiverId));
    }

    // 안 읽은 메시지 개수 조회
    @GetMapping("/message/unread/count")
    public ResponseEntity<Map<String, Object>> getUnreadMsg(@RequestParam(required = false) Long empId) {
        if (empId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "❌ empId가 필요합니다.");
        }
        int unreadCount = ms.getUnreadMsg(empId);
        Map<String, Object> result = new HashMap<>();
        result.put("unreadCount", unreadCount);
        return ResponseEntity.ok(result);
    }

    // 메신저 방 삭제
    @DeleteMapping("/message/delete")
    public ResponseEntity<Map<String, Object>> deleteMessage(
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = true) Long empId,
            @RequestParam(required = false) Long otherEmpId) {
        boolean isDeleted = ms.deleteMessage(roomId, empId, otherEmpId);
        Map<String, Object> result = new HashMap<>();
        if (isDeleted) {
            result.put("message", "대화방이 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(result);
        } else {
            result.put("message", "삭제할 대화방을 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // 방 참여자 조회
    @GetMapping("/room/participants")
    public ResponseEntity<List<Map<String, Object>>> getRoomParticipants(@RequestParam int roomId) {
        // Service에서 방 참여자 목록을 조회
        List<Map<String, Object>> participants = ms.getRoomParticipants(roomId);
        return ResponseEntity.ok(participants);
    }

    // 대화방 삭제 (메시지, 참여자, 대화방 정보 모두 삭제)
    @DeleteMapping("/room/delete")
    public ResponseEntity<Map<String, Object>> deleteChatRoom(@RequestParam int roomId) {
        boolean isDeleted = ms.deleteChatRoom(roomId);
        Map<String, Object> result = new HashMap<>();
        if (isDeleted) {
            result.put("message", "대화방이 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(result);
        } else {
            result.put("message", "대화방 삭제에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    // 단체 방 생성
    @PostMapping("/room/create")
    public ResponseEntity<Map<String, Object>> createRoom(@RequestBody Map<String, Object> roomData) {
        // roomData 예: { roomName: "새로운 단체 대화" }
        int result = ms.createMessengerRoom(roomData);
        if (result > 0) {
            // useGeneratedKeys로 roomId가 roomData에 세팅됨 (BigInteger)
            BigInteger bigRoomId = (BigInteger) roomData.get("roomId");
            int roomId = bigRoomId.intValue();

            Map<String, Object> response = new HashMap<>();
            response.put("roomId", roomId);
            response.put("message", "단체 대화방 생성 완료");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "단체 대화방 생성 실패"));
        }
    }

    // 방 참여자 추가
    @PostMapping("/room/participant/add")
    public ResponseEntity<Map<String, Object>> addRoomParticipant(@RequestBody Map<String, Object> participantData) {
        // participantData 예: { "roomId": 123, "empId": 1 }
        ms.addRoomParticipant(participantData);
        // addRoomParticipant가 내부에서 DB insert를 수행
        Map<String, Object> result = new HashMap<>();
        result.put("message", "방 참여자 추가 완료");
        return ResponseEntity.ok(result);
    }

    // 단체 대화방 조회
    @GetMapping("/room/list")
    public ResponseEntity<List<Map<String, Object>>> getRoomList(@RequestParam Long empId) {
        // MessengerService에 getGroupRoomList(empId) 메서드를 구현합니다.
        List<Map<String, Object>> rooms = ms.getGroupRoomList(empId);
        return ResponseEntity.ok(rooms);
    }

    // 단체 방 내용 조회
    @GetMapping("/room/messages")
    public ResponseEntity<?> getMessagesByRoomId(@RequestParam int roomId) {
        try {
            List<Map<String, Object>> messages = ms.getMessagesByRoomId(roomId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("메시지 조회 실패: " + e.getMessage());
        }
    }

    // 첨부파일 업로드
    @PostMapping("/file/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "D:/erp_fileupload/"; // ✅ 윈도우 경로
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // 폴더 없으면 생성

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File target = new File(uploadDir + fileName);
            file.transferTo(target);

            return ResponseEntity.ok("/uploads/" + fileName); // 프론트에서 이 경로로 접근
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패: " + e.getMessage());
        }
    }

    // 첨부파일 다운로드
    @GetMapping("/file/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) throws IOException {
        String uploadDir = "D:/erp_fileupload/";
        File file = new File(uploadDir + filename);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename) // ✅ 핵심!
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    // 메시지 읽음 처리 API 추가
    @PostMapping("/message/markAsRead")
    public ResponseEntity<Map<String, Object>> markMessageAsRead(@RequestParam Long empId, @RequestParam Long roomId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("empId", empId);
        paramMap.put("roomId", roomId);

        ms.markMessagesAsRead(empId, roomId);  // 서비스에서 처리

        Map<String, Object> result = new HashMap<>();
        result.put("message", "메시지가 읽음 상태로 처리되었습니다.");
        return ResponseEntity.ok(result);
    }

}
