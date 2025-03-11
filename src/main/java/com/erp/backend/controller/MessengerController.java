package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.model.FileVO;
import com.erp.backend.model.MessengerVO;
import com.erp.backend.service.MessengerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messenger")
public class MessengerController {

    @Autowired
    private MessengerService ms;

    // 세션 기반 로그인 사용자 ID 반환 ==> JWT 기반으로 변경할 것!!
    private Long getLoginEmpId(HttpSession session) {
        MemberDto user = (MemberDto) session.getAttribute("user");
        if(user == null) {
            throw new RuntimeException("로그인이 필요합니다");
        }
        return Long.valueOf(String.valueOf(user.getEmpId()));
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

    // 선택 직원 가져오기
    @GetMapping("/emp/selected")
    public List<Map<String, String>> getChosenEmp(HttpSession session) {
        Long empId = getLoginEmpId(session);
        return ms.getChosenEmp(empId);
    }

    // 메신저 보내기
    @PostMapping("/send")
    public String sendMessage(@RequestBody MessengerVO mvo, HttpSession session) {
        mvo.setSenderId(getLoginEmpId(session));
        ms.sendMessage(mvo);
        return "메시지를 성공적으로 보냈습니다.";
    }

    // 전체 메시지 목록 조회
    @GetMapping("message/list")
    public Map<String, List<Map<String, String>>> getMessageList(HttpSession session) {
        Long empId = getLoginEmpId(session);
        // 보낸 메시지 조회
        List<Map<String, String>> sendMsg = ms.getSendMsg(Map.of("empId", String.valueOf(empId)));
        // 받은 메시지 조회
        List<Map<String, String>> receiveMsg = ms.getReceivedMsg(Map.of("empId", String.valueOf(empId)));
        return Map.of("sendMsg", sendMsg, "receiveMsg", receiveMsg);
    }

    // 보낸 메시지 내용 조회
    @GetMapping("/message/content/send")
    public Map<String, String> getSendMsgContent(@RequestParam Long msgId ,HttpSession session) {
        getLoginEmpId(session);
        return ms.getMsgContent(String.valueOf(msgId));
    }

    // 받은 메시지 내용 조회
    @GetMapping("/message/content/receive")
    public Map<String, String> getReceiveMsgContent(@RequestParam Long msgId ,HttpSession session) {
        getLoginEmpId(session);
        return ms.getMsgContent2(String.valueOf(msgId));
    }

    // 안읽은 메신저 읽기
    @PostMapping("/read")
    public String readMessage(HttpSession session) {
        Long empId = getLoginEmpId(session);
        ms.updateAllMsg(empId);
        return "모든 메시지를 읽음 처리했습니다.";
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
    public String deliverMessage(@RequestBody Map<String, MessengerVO> msg, HttpSession session) {
        MessengerVO mvo = msg.get("mvo");
        MessengerVO mvo1 = msg.get("mvo1");
        mvo.setSenderId(getLoginEmpId(session));
        ms.deliverMessage(mvo, mvo1);
        return "메시지 전달 성공";
    }

    // 메신저 방 개수
    @GetMapping("/room/count")
    public int getRoomCount(HttpSession session) {
        Long empId = getLoginEmpId(session);
        return ms.getTotalMsg(Map.of("empId", String.valueOf(empId)));
    }

    // 메시지 발송을 위한 사람 이름 조회
    @GetMapping("/send/name")
    public String getEmpName(HttpSession session) {
        Long empId = getLoginEmpId(session);
        return ms.getEmpName(empId);
    }

    // 안읽은 메신저 개수
    @GetMapping("/read/not/count")
    public int getUnreadMsg(HttpSession session) {
        Long empId = getLoginEmpId(session);
        return ms.getUnreadMsg(empId);
    }
}
