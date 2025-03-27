package com.erp.backend.service;

import com.erp.backend.dto.GroupMessageDTO;
import com.erp.backend.model.FileVO;
import com.erp.backend.model.MessengerVO;

import java.util.List;
import java.util.Map;

public interface InterMessengerService {

    // 전체 부서 조회
    List<Map<String, Object>> getDept();

    // 부서 직원 조회
    List<Map<String, Object>> getDeptPerson(Map<String, Object> map);

    // 선택 직원 가져오기
    List<Map<String, Object>> getChosenEmp(Long empId);

    // 메신저 보내기
    void sendMessage(MessengerVO msgvo);

    // 단체 메시지 전송
    int sendGroupMessage(GroupMessageDTO groupMsg);

    // 보낸 메시지 리스트 조회
    List<Map<String, Object>> getSendMsg(Map<String, Object> paramMap);

    // 받은 메시지 리스트 조회
    List<Map<String, Object>> getReceivedMsg(Map<String, Object> paramMap);

    // 메신저 내용 조회(보낸 메신저, 받은 메신저)
    Map<String, Object> getMsgContent(Long msgId, Long senderId);
    Map<String, Object> getMsgContent2(Long msgId, Long receiverId);

    // 안읽은 메신저 읽기
    void updateAllMsg(Long empId);

    // 첨부파일 추가
    void addFile(FileVO filevo);

    // 메신저 첨부파일 조회
    List<FileVO> getMsgFile(Long msgId);

    // 메신저 전달
    void deliverMessage(MessengerVO dvo, MessengerVO mvo);

    // 메신저 방 조회 (1:1, 단체)
    int getTotalMsg(Map<String, String> map);

    // 메신저 발송을 위한 사람 이름 조회
    Map<String, Object> getEmpName(Long receiverId);

    // 기존 메시지 가져오기
    MessengerVO getMessageById(Long msgId);

    // 안읽은 메신저 개수 조회
    int getUnreadMsg(Long empId);

    // 메시지 방 삭제
    boolean deleteMessage(Long roomId, Long empId, Long otherEmpId);

    // 단체 메시지용 메신저 룸 생성
    int createMessengerRoom(Map<String, Object> roomParams);

    // 단체 메시지용 메신저 룸 참여자 추가
    void addRoomParticipant(Map<String, Object> participantParams);

    // 방 참여자 조회
    List<Map<String, Object>> getRoomParticipants(int roomId);

    // 방 삭제 관련
    boolean deleteChatRoom(int roomId);

    // 단체 방 조회
    List<Map<String, Object>> getGroupRoomList(Long empId);

    // 단체 방 내용 조회
    List<Map<String, Object>> getMessagesByRoomId(int roomId);

    // 메시지 읽음 상태 업데이트
    void markMessagesAsRead(Long empId, Long roomId);

}
