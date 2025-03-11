package com.erp.backend.service;

import com.erp.backend.model.FileVO;
import com.erp.backend.model.InterMessengerDao;
import com.erp.backend.model.MessengerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class MessengerService implements InterMessengerService {

    @Autowired
    private InterMessengerDao imDao;

    // 전체 부서 조회
    @Override
    public List<Map<String, String>> getDept() {
        return imDao.getDept();
    }

    // 부서 직원 조회
    @Override
    public List<Map<String, String>> getDeptPerson(Map<String, String> map) {
        return imDao.getDeptPerson(map);
    }

    // 해당 부서 팀 조회
    @Override
    public List<Map<String, String>> getTeam(String dept) {
        return imDao.getTeam(dept);
    }

    // 선택 직원 조회
    @Override
    public List<Map<String, String>> getChosenEmp(Long empId) {
        return imDao.getChosenEmp(empId);
    }

    // 메신저 보내기
    @Override
    public String sendMessage(MessengerVO mvo) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(calendar.getTime());

        Long receiverId = mvo.getReceiverId();

        StringBuilder sb = new StringBuilder();
        sb.append("insert into messenger (group_id, filepath, messenger_id, sender_id, receiver_id, content) values (");
        sb.append(time).append(", ")
                .append(mvo.getFilePath()).append(", ")
                .append(mvo.getMessengerId()).append(", ")
                .append(mvo.getSenderId()).append(", ")
                .append(receiverId).append(", '")
                .append(mvo.getContent()).append("')");

        imDao.sendMessage(sb.toString());
        return time;
    }

    // 보낸 메일 리스트
    @Override
    public List<Map<String, String>> getSendMsg(Map<String, String> map) {
        return imDao.getSendMsg(map);
    }

    // 받은 메일 리스트
    @Override
    public List<Map<String, String>> getReceivedMsg(Map<String, String> map) {
        return imDao.getReceivedMsg(map);
    }

    // 보낸 메신저 내용 조회
    @Override
    public Map<String, String> getMsgContent(String content) {
        return imDao.getMsgContent(content);
    }

    // 받은 메신저 내용 조회
    @Override
    public Map<String, String> getMsgContent2(String content) {
        return imDao.getMsgContent2(content);
    }

    // 안읽은 메신저 읽기
    @Override
    public void updateAllMsg(Long empId) {
        imDao.updateAllMsg(empId);
    }

    // 메신저 첨부파일 추가
    @Override
    public void addFile(FileVO filevo) {
        imDao.addFile(filevo);
    }

    // 첨부파일 조회
    @Override
    public List<FileVO> getMsgFile(Long messengerId) {
        return imDao.getMsgFile(String.valueOf(messengerId));
    }

    // 메신저 전달
    @Override
    public void deliverMessage(MessengerVO dvo, MessengerVO mvo) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(calendar.getTime());

        Long receiverId = dvo.getReceiverId();

        StringBuilder sb = new StringBuilder();
        sb.append("insert into messenger (group_id, filepath, messenger_id, sender_id, receiver_id, content) values (");
        sb.append(dvo.getMessengerId()).append(", ")
                .append(dvo.getFilePath()).append(", ")
                .append(time).append(", ")
                .append(mvo.getSenderId()).append(", ")
                .append(receiverId).append(", '")
                .append(mvo.getContent()).append("')");

        imDao.sendMessage(sb.toString());
    }

    // 메신저 방 개수
    @Override
    public int getTotalMsg(Map<String, String> map) {
        return imDao.getTotalMsg(map);
    }

    // 메시지 발송을 위한 사람 이름 조회
    @Override
    public String getEmpName(Long empId) {
        return imDao.getEmpName(empId);
    }

    // 안읽은 메신저 개수
    @Override
    public int getUnreadMsg(Long empId) {
        return imDao.getUnreadMsg(empId);
    }
}