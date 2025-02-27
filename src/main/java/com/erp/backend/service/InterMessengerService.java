package com.erp.backend.service;

import com.erp.backend.model.FileVO;
import com.erp.backend.model.MessengerVO;

import java.util.List;
import java.util.Map;

public interface InterMessengerService {

    // 전체 부서 조회
    List<Map<String, String>> getDept();

    // 부서 직원 조회
    List<Map<String, String>> getDeptPerson(Map<String, String> map);

    // 해당부서 팀 구해오기
    List<Map<String, String>> getTeam(String dept);

    // 선택 직원 가져오기
    List<Map<String, String>> getChosenEmp(String empId);

    // 메신저 보내기
    String sendMessage(MessengerVO msgvo);

    // 보낸 메일 리스트 가져오기
    List<Map<String, String>> getSendMsg(Map<String, String> map);

    // 받은 메일 리스트 가져오기
    List<Map<String, String>> getReceivedMsg(Map<String, String> map);

    // 메신저 내용 조회(보낸 메신저, 받은 메신저)
    Map<String, String>  getMsgContent(String content);
    Map<String, String>  getMsgContent2(String content);

    // 안읽은 메신저 읽기
    void updateAllMsg(String empId);

    // 메신저 파일 첨부
    void addFile(FileVO filevo);

    // 메신저 첨부파일 조회
    List<FileVO> getMsgFile(String content);

    // 메신저 전달
    void deliverMessage(MessengerVO dvo, MessengerVO mvo);

    // 메신저 방 조회 (1:1, 단체)
    int getTotalMsg(Map<String, String> map);

    // 메신저 발송을 위한 사람 이름 조회
    String getEmpName(String empId);

    // 안읽은 메신저 개수 조회
    int getUnreadMsg(String empId);
}
