package com.erp.backend.model;

import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
@Primary
public class MessengerDao implements InterMessengerDao {

    @Resource
    private SqlSessionTemplate sqlsession;

    // 전체부서 조회
    @Override
    public List<Map<String, String>> getDept() {
        return sqlsession.selectList("selectDept");
    }

    // 부서 직원 조회
    @Override
    public List<Map<String, String>> getDeptPerson(Map<String, String> map) {
        return sqlsession.selectList("selectDeptPerson", map);
    }

    // 해당부서 팀 조회
    @Override
    public List<Map<String, String>> getTeam(String dept) {
        return sqlsession.selectList("selectTeam", dept);
    }

    // 선택 직원 조회
    @Override
    public List<Map<String, String>> getChosenEmp(Long empId) {
        return sqlsession.selectList("selectChosenEmp", empId);
    }

    // 메신저 보내기
    @Override
    public void sendMessage(String sql) {
        sqlsession.insert("insertMessage", sql);
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

    // 메신저 첨부파일 추가
    @Override
    public void addFile(FileVO filevo) {
        sqlsession.insert("insertFile", filevo);
    }

    // 첨부파일 조회
    @Override
    public List<FileVO> getMsgFile(String content) {
        return sqlsession.selectList("selectMsgFile", content);
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

}
