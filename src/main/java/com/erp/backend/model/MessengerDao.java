package com.erp.backend.model;

import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@Primary
public class MessengerDao implements InterMessengerDao {

    @Resource
    private SqlSessionTemplate sqlsession;

    // 전체부서 조회
    @Override
    public List<Map<String, Object>> getDept() {
        List<Map<String, Object>> result = sqlsession.selectList("selectDept");
        System.out.println("✅ 부서 조회 결과: " + result);
        return result != null ? result : Collections.emptyList();
    }

    // 부서 직원 조회
    @Override
    public List<Map<String, Object>> getDeptPerson(Map<String, Object> map) {
        List<Map<String, Object>> result = sqlsession.selectList("selectDeptPerson", map);
        System.out.println("✅ 부서 직원 조회 결과: " + result);
        return result != null ? result : Collections.emptyList();
    }

    // 선택 직원 조회
    @Override
    public List<Map<String, Object>> getChosenEmp(Long empId) {
        List<Map<String, Object>> result = sqlsession.selectList("selectChosenEmp", Map.of("empId", empId));
        System.out.println("✅ 선택된 직원 조회 결과: " + result);
        return result != null ? result : Collections.emptyList();
    }

    // 메신저 보내기
    @Override
    public void sendMessage(MessengerVO msgvo) {
        sqlsession.insert("sendMessage", msgvo);
    }

    // 메신저 전달
    @Override
    public void deliverMessage(MessengerVO newMessage) {
        sqlsession.insert("deliverMessage", newMessage);
    }

    // sender or receiver 존재 확인
    @Override
    public boolean isEmployeeExists(Long empId) {
        return sqlsession.selectOne("isEmployeeExists", empId);
    }

    // 메시지 존재 여부 확인
    @Override
    public boolean isMessageExists(Long msgId) {
        Integer count = sqlsession.selectOne("isMessageExists", msgId);
        return count != null && count > 0;
    }

    // 기존 메시지 가져오기
    @Override
    public MessengerVO getMessageById(Long msgId) {
        return sqlsession.selectOne("getMessageById", msgId);
    }

    // 보낸 메일 리스트
    @Override
    public List<Map<String, Object>> getSendMsg(Map<String, Object> paramMap) {
        return sqlsession.selectList("com.erp.backend.model.InterMessengerDao.selectSendMsg", paramMap);
    }

    // 받은 메일 리스트
    @Override
    public List<Map<String, Object>> getReceivedMsg(Map<String, Object> paramMap) {
        return sqlsession.selectList("com.erp.backend.model.InterMessengerDao.selectReceivedMsg", paramMap);
    }

    // 보낸 메신저 내용 조회
    @Override
    public Map<String, Object> getMsgContent(Map<String, Object> paramMap) {
        return sqlsession.selectOne("selectMsgContent", paramMap);
    }

    // 받은 메신저 내용 조회
    @Override
    public Map<String, Object> getMsgContent2(Map<String, Object> paramMap) {
        return sqlsession.selectOne("selectMsgContent2", paramMap);
    }

    // 안읽은 메신저 읽기
    @Override
    public void updateAllMsg(Map<String, Object> paramMap) {
        sqlsession.update("com.erp.backend.model.InterMessengerDao.updateAllMsg", paramMap);
    }

    // 첨부파일 조회
    @Override
    public List<FileVO> getMsgFile(Long msgId) {
        return sqlsession.selectList("selectMsgFile", msgId);
    }

    // 첨부파일 추가
    @Override
    public void addFile(FileVO fileVO) {
        sqlsession.insert("insertFile", fileVO);
    }

    // 메신저 방 개수
    @Override
    public int getTotalMsg(Map<String, String> map) {
        return sqlsession.selectOne("selectTotalMsg", map);
    }

    // 메신저 발송을 위한 사람 조회
    @Override
    public Map<String, Object> getEmpName(Map<String, Object> paramMap) {
        return sqlsession.selectOne("selectRecipientInfo", paramMap);
    }

    // 안읽은 메신저 개수 조회
    @Override
    public int getUnreadMsg(Map<String, Object> paramMap) {
        return sqlsession.selectOne("com.erp.backend.model.InterMessengerDao.selectUnreadMsg", paramMap);
    }

    // 메신저 방 삭제
    @Override
    public int deleteMessage(Map<String, Object> paramMap) {
        return sqlsession.delete("deleteMessage", paramMap);
    }
}
