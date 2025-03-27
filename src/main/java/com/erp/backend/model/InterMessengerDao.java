package com.erp.backend.model;

import org.apache.ibatis.annotations.Mapper;

import java.io.File;
import java.util.List;
import java.util.Map;
import com.erp.backend.dto.GroupMessageDTO;

@Mapper
public interface InterMessengerDao {

    // 전체 부서 조회
    List<Map<String, Object>> getDept();

    // 부서 직원 조회
    List<Map<String, Object>> getDeptPerson(Map<String, Object> map);

    // 선택 직원 가져오기
    List<Map<String, Object>> getChosenEmp(Long empId);

    // 메신저 보내기
    void sendMessage(MessengerVO msgvo);

    // 메시지 전달 기능
    void deliverMessage(MessengerVO newMessage);

    // senderId 또는 receiverId가 employee 테이블에 존재하는지 확인
    int isEmployeeExists(Long empId);

    // 메시지 존재 여부 확인
    boolean isMessageExists(Long msgId);

    // 기존 메시지를 가져오는 메서드 추가
    MessengerVO getMessageById(Long msgId);

    // 보낸 메시지 리스트 조회
    List<Map<String, Object>> getSendMsg(Map<String, Object> paramMap);

    // 받은 메시지 리스트 조회
    List<Map<String, Object>> getReceivedMsg(Map<String, Object> paramMap);

    // 메신저 내용 조회(보낸 메신저, 받은 메신저)
    Map<String, Object> getMsgContent(Map<String, Object> paramMap);
    Map<String, Object> getMsgContent2(Map<String, Object> paramMap);

    // 안읽은 메신저 읽기
    void updateAllMsg(Map<String, Object> paramMap);

    // 메신저 첨부파일 조회
    List<FileVO> getMsgFile(Long msgId);

    // 첨부파일 추가
    void addFile(FileVO fileVO);

    // 메신저 방 조회 (1:1, 단체)
    int getTotalMsg(Map<String, String> map);

    // 메신저 발송을 위한 사람 이름 조회
    Map<String, Object> getEmpName(Map<String, Object> paramMap);

    // 안읽은 메신저 개수 조회
    int getUnreadMsg(Map<String, Object> paramMap);

    // 메시지 방 삭제
    int deleteMessage(Map<String, Object> paramMap);

    // 메신저 룸 생성
    int createMessengerRoom(Map<String, Object> roomParams);

    // 메신저 룸 참여자 추가
    void addRoomParticipant(Map<String, Object> participantParams);

    // 방 참여자 조회
    List<Map<String, Object>> selectRoomParticipants(int roomId);

    // 방 삭제 관련
    int deleteMessagesByRoomId(int roomId);
    int deleteRoomParticipants(int roomId);
    int deleteMessengerRoom(int roomId);

    // 단체 방 조회
    List<Map<String, Object>> selectGroupRooms(Long empId);

    // 단체 방 내용 조회
    List<Map<String, Object>> selectMessagesByRoomId(int roomId);

    // 단체 방 첨부파일
    void sendGroupMessage(GroupMessageDTO groupMsg);

    // 메시지 읽음 상태 업데이트
    void updateMessagesReadStatus(Map<String, Object> paramMap);

}
