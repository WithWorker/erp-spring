package com.erp.backend.model;

import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MessengerDao implements InterMessengerDao {

    @Resource
    private SqlSessionTemplate sqlsession;

    // 전체부서 조회
    @Override
    public List<Map<String, String>> getDept() {
        String dept = "";
        List<Map<String, String>> deptList = sqlsession.selectList("selectDept", dept);
        return deptList;
    }

    // 부서 직원 조회
    @Override
    public List<Map<String, String>> getDeptPerson(Map<String, String> map) {
        List<Map<String, String>> deptPerson = sqlsession.selectList("selectDeptPerson", map);
        return deptPerson;
    }

    // 해당부서 팀 조회
    @Override
    public List<Map<String, String>> getTeam(String dept) {
        List<Map<String, String>> team = sqlsession.selectList("selectTeam", dept);
        return team;
    }

    // 선택 직원 조회
    @Override
    public List<Map<String, String>> getChosenEmp(String empId) {
        List<Map<String, String>> chosenEmp = sqlsession.selectList("selectChosenEmp", empId);
        return chosenEmp;
    }

    // 메신저 보내기
    @Override
    public void sendMessage(String sql) {
        sqlsession.insert("insertMessage", sql);
    }

    // 보낸 메일 리스트
    @Override
    public List<Map<String, String>> getSendMsg(Map<String, String> map) {
        List<Map<String, String>> sendMsg = sqlsession.selectList("selectSendMsg", map);
        return sendMsg;
    }

    // 받은 메일 리스트
    @Override
    public List<Map<String, String>> getReceivedMsg(Map<String, String> map) {
        List<Map<String, String>> receivedMsg = sqlsession.selectList("selectReceivedMsg", map);
        return receivedMsg;
    }

    // 보낸 메신저 내용 조회
    @Override
    public Map<String, String> getMsgContent(String content) {
        Map<String, String> msgContent = sqlsession.selectOne("selectMsgContent", content);
        return msgContent;
    }

    // 받은 메신저 내용 조회
    @Override
    public Map<String, String> getMsgContent2(String content) {
        Map<String, String> msgContent2 = sqlsession.selectOne("selectMsgContent2", content);
        return msgContent2;
    }

    // 안읽은 메신저 읽기
    @Override
    public void updateAllMsg(String empId) {
        sqlsession.update("updateAllMsg", empId);
    }

    // 메신저 첨부파일 추가
    @Override
    public void addFile(FileVO filevo) {
        sqlsession.insert("insertFile", filevo);
    }

    // 첨부파일 조회
    @Override
    public List<FileVO> getMsgFile(String messengerId) {
        List<FileVO> msgFile = sqlsession.selectList("selectMsgFile", messengerId);
        return msgFile;
    }

    // 메신저 방 개수
    @Override
    public int getTotalMsg(Map<String, String> map) {
        int totalMsg = sqlsession.selectOne("selectTotalMsg", map);
        return totalMsg;
    }

    // 메신저 발송을 위한 사람 조회
    @Override
    public String getEmpName(String empId) {
        String empName = sqlsession.selectOne("selectEmpName", empId);
        return empName;
    }

    // 안읽은 메신저 개수 조회
    @Override
    public int getUnreadMsg(String empId) {
        int unreadMsg = sqlsession.selectOne("selectUnreadMsg", empId);
        return unreadMsg;
    }

}
