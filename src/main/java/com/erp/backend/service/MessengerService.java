package com.erp.backend.service;

import com.erp.backend.dto.GroupMessageDTO;
import com.erp.backend.model.FileVO;
import com.erp.backend.model.InterMessengerDao;
import com.erp.backend.model.MessengerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessengerService implements InterMessengerService {

    private final InterMessengerDao imDao;

    @Autowired
    public MessengerService(@Qualifier("messengerDao") InterMessengerDao imDao) {  // ✅ 명확하게 지정
        this.imDao = imDao;
    }

    // 전체 부서 조회
    @Override
    public List<Map<String, Object>> getDept() {
        return imDao.getDept();
    }

    // 부서 직원 조회
    @Override
    public List<Map<String, Object>> getDeptPerson(Map<String, Object> map) {
        return imDao.getDeptPerson(map);
    }

    // 선택 직원 조회
    @Override
    public List<Map<String, Object>> getChosenEmp(Long empId) {
        return imDao.getChosenEmp(empId);
    }

    // 메신저 보내기
    @Override
    public void sendMessage(MessengerVO msgvo) {
        if (msgvo == null || msgvo.getSenderId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "senderId가 필요합니다.");
        }
        // roomId가 있으면 단체 대화로 간주
        boolean isGroupChat = (msgvo.getRoomId() != null && msgvo.getRoomId() > 0);

        if (!isGroupChat) { // 1:1 대화일 때만 receiverId 검사
            if (msgvo.getReceiverId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "receiverId가 필요합니다.");
            }
            int senderExists = imDao.isEmployeeExists(msgvo.getSenderId());
            int receiverExists = imDao.isEmployeeExists(msgvo.getReceiverId());
            if (senderExists != 1 || receiverExists != 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "데이터 무결성 오류: senderId 또는 receiverId가 employee 테이블에 존재하지 않습니다.");
            }
        } else { // 단체 대화인 경우, senderId만 검사
            int senderExists = imDao.isEmployeeExists(msgvo.getSenderId());
            if (senderExists != 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "데이터 무결성 오류: senderId가 employee 테이블에 존재하지 않습니다.");
            }
        }

        try {
            imDao.sendMessage(msgvo);
            System.out.println("✅ 메시지 전송 완료: msgId=" + msgvo.getMessengerId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류 발생: " + e.getMessage());
        }
    }

    // 메신저 전달
    @Override
    public void deliverMessage(MessengerVO originalMessage, MessengerVO newMessage) {
        if (originalMessage == null || newMessage == null) {
            throw new IllegalArgumentException("전달할 메시지 정보가 필요합니다.");
        }
        int senderExists = imDao.isEmployeeExists(newMessage.getSenderId());
        int receiverExists = imDao.isEmployeeExists(newMessage.getReceiverId());
        if (senderExists != 1 || receiverExists != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "데이터 무결성 오류: senderId 또는 receiverId가 employee 테이블에 존재하지 않습니다.");
        }
        if (!imDao.isMessageExists(originalMessage.getMessengerId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "전달할 메시지가 존재하지 않습니다.");
        }
        try {
            imDao.sendMessage(newMessage);
            System.out.println("✅ 메시지 전달 완료: msgId=" + newMessage.getMessengerId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "메시지 전달 실패: " + e.getMessage());
        }
    }

    // 기존 메시지 가져오기
    @Override
    public MessengerVO getMessageById(Long msgId) {
        return imDao.getMessageById(msgId);
    }

    // 보낸 메시지 리스트
    @Override
    public List<Map<String, Object>> getSendMsg(Map<String, Object> paramMap) {
        return imDao.getSendMsg(paramMap);
    }

    // 받은 메시지 리스트
    @Override
    public List<Map<String, Object>> getReceivedMsg(Map<String, Object> paramMap) {
        return imDao.getReceivedMsg(paramMap);
    }

    // 보낸 메신저 내용 조회
    @Override
    public Map<String, Object> getMsgContent(Long msgId, Long senderId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("messageId", msgId);
        paramMap.put("senderId", senderId);

        Map<String, Object> result = imDao.getMsgContent(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("MyBatis에서 결과 없음! msgId: " + msgId + ", senderId: " + senderId);
            return Collections.emptyMap();
        }

        return result;
    }

    // 받은 메신저 내용 조회
    public Map<String, Object> getMsgContent2(Long msgId, Long receiverId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("messageId", msgId);
        paramMap.put("receiverId", receiverId);

        Map<String, Object> result = imDao.getMsgContent2(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("MyBatis에서 결과 없음! msgId: " + msgId + ", receiverId: " + receiverId);
            return Collections.emptyMap();
        }

        return result;
    }

    // 안읽은 메신저 읽기
    @Override
    public void updateAllMsg(Long empId) {
        if (empId == null) {
            throw new IllegalArgumentException("empId가 NULL입니다.");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("empId", empId);

        imDao.updateAllMsg(paramMap);
    }

    // 첨부파일 추가
    @Override
    public void addFile(FileVO fileVO) {
        if (fileVO.getMessengerId() == null || fileVO.getFilePath() == null) {
            throw new IllegalArgumentException("MessengerId와 filePath는 필수입니다.");
        }
        // 파일명 추출 (경로에서 마지막 "/" 이후 값)
        String fileName = fileVO.getFilePath().substring(fileVO.getFilePath().lastIndexOf("/") + 1);
        fileVO.setFileName(fileName);

        imDao.addFile(fileVO);
    }

    // 첨부파일 조회
    @Override
    public List<FileVO> getMsgFile(Long msgId) {
        if (msgId == null) {
            throw new IllegalArgumentException("msgId가 필요합니다.");
        }

        List<FileVO> fileList = imDao.getMsgFile(msgId);
        if (fileList == null || fileList.isEmpty()) {
            return List.of();
        }
        return fileList;
    }

    // 메신저 방 개수
    @Override
    public int getTotalMsg(Map<String, String> map) {
        return imDao.getTotalMsg(map);
    }

    // 메시지 발송을 위한 사람 이름 조회
    @Override
    public Map<String, Object> getEmpName(Long receiverId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("receiverId", receiverId);

        Map<String, Object> result = imDao.getEmpName(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("조회 결과 없음! receiverId: " + receiverId);
            return Collections.emptyMap();
        }
        return result;
    }

    // 안읽은 메신저 개수
    @Override
    public int getUnreadMsg(Long empId) {
        return imDao.getUnreadMsg(Map.of("empId", empId));
    }

    // 메신저 방 삭제
    @Override
    public boolean deleteMessage(Long roomId, Long empId, Long otherEmpId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("roomId", roomId);
        paramMap.put("empId", empId);
        paramMap.put("otherEmpId", otherEmpId);
        int result = imDao.deleteMessage(paramMap);
        return result > 0;
    }

    // 단체 메시지 전송 (GroupMessageDTO를 받아 각 수신자에게 단일 메시지 전송)
    @Override
    public int sendGroupMessage(GroupMessageDTO groupMsg) {
        // roomId 없으면 방 생성
        if (groupMsg.getRoomId() == null || groupMsg.getRoomId() == 0) {
            // 방 생성
            Map<String, Object> roomParams = new HashMap<>();
            roomParams.put("roomName", "단체 대화방");
            imDao.createMessengerRoom(roomParams);
            int roomId = ((BigInteger) roomParams.get("roomId")).intValue();
            groupMsg.setRoomId(roomId);

            // 참여자 등록 (중복 제거)
            Set<Long> uniqueIds = new HashSet<>(groupMsg.getReceiverIds());
            uniqueIds.add(groupMsg.getSenderId()); // 보낸 사람도 포함

            for (Long empId : uniqueIds) {
                Map<String, Object> participant = new HashMap<>();
                participant.put("roomId", roomId);
                participant.put("empId", empId);
                imDao.addRoomParticipant(participant);
            }
        }

        // 메시지 저장
        MessengerVO msgvo = new MessengerVO();
        msgvo.setSenderId(groupMsg.getSenderId());
        msgvo.setReceiverId(null); // 단체는 receiverId 없음
        msgvo.setRoomId(groupMsg.getRoomId());
        msgvo.setContent(groupMsg.getContent());
        msgvo.setFilePath(groupMsg.getFilePath());
        imDao.sendGroupMessage(groupMsg);
        groupMsg.setMessengerId(msgvo.getMessengerId());

        return groupMsg.getRoomId();
    }

    @Override
    public int createMessengerRoom(Map<String, Object> roomParams) {
        // DAO의 createMessengerRoom 메서드를 호출
        // roomParams에 방 이름 등 필요한 값이 포함
        // useGeneratedKeys="true"로 roomId가 roomParams에 설정
        return imDao.createMessengerRoom(roomParams);
    }

    @Override
    public void addRoomParticipant(Map<String, Object> participantParams) {
        // DAO의 addRoomParticipant 메서드를 호출
        imDao.addRoomParticipant(participantParams);
    }

    // 방 참여자 조회
    @Override
    public List<Map<String, Object>> getRoomParticipants(int roomId) {
        return imDao.selectRoomParticipants(roomId);
    }

    // 방 삭제 관련
    @Override
    @Transactional
    public boolean deleteChatRoom(int roomId) {
        try {
            // 해당 방의 메시지 삭제
            imDao.deleteMessagesByRoomId(roomId);
            // 해당 방의 참여자 삭제
            imDao.deleteRoomParticipants(roomId);
            // 대화방 정보 삭제
            int deletedRooms = imDao.deleteMessengerRoom(roomId);
            return deletedRooms > 0;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "대화방 삭제 실패: " + e.getMessage());
        }
    }

    // 단체 대화방 조회
    @Override
    public List<Map<String, Object>> getGroupRoomList(Long empId) {
        // MessengerDao에서 단체 대화방 목록을 조회하는 쿼리를 호출
        return imDao.selectGroupRooms(empId);
    }

    // 단체 방 내용 조회
    @Override
    public List<Map<String, Object>> getMessagesByRoomId(int roomId) {
        return imDao.selectMessagesByRoomId(roomId);
    }

    // 메시지 읽음 상태 업데이트
    @Override
    public void markMessagesAsRead(Long empId, Long roomId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("empId", empId);
            paramMap.put("roomId", roomId);
            imDao.updateMessagesReadStatus(paramMap);  // DAO 호출
        } catch (Exception e) {
            throw new RuntimeException("메시지 읽음 상태 업데이트 실패: " + e.getMessage());
        }
    }

}