package com.erp.backend.service;

import com.erp.backend.model.FileVO;
import com.erp.backend.model.InterMessengerDao;
import com.erp.backend.model.MessengerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

        System.out.println("🔍 실행할 쿼리 파라미터: " + paramMap);

        Map<String, Object> result = imDao.getMsgContent(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("🚨 MyBatis에서 결과 없음! msgId: " + msgId + ", senderId: " + senderId);
            return Collections.emptyMap();
        }

        System.out.println("✅ 조회 결과: " + result);
        return result;
    }

    // 받은 메신저 내용 조회
    public Map<String, Object> getMsgContent2(Long msgId, Long receiverId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("messageId", msgId);
        paramMap.put("receiverId", receiverId);

        System.out.println("🔍 실행할 쿼리 파라미터: " + paramMap);

        Map<String, Object> result = imDao.getMsgContent2(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("🚨 MyBatis에서 결과 없음! msgId: " + msgId + ", receiverId: " + receiverId);
            return Collections.emptyMap();
        }

        System.out.println("✅ 조회 결과: " + result);
        return result;
    }

    // 안읽은 메신저 읽기
    @Override
    public void updateAllMsg(Long empId) {
        if (empId == null) {
            throw new IllegalArgumentException("❌ empId가 NULL입니다.");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("empId", empId);

        imDao.updateAllMsg(paramMap);
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
    public Map<String, Object> getEmpName(Long receiverId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("receiverId", receiverId);

        System.out.println("🔍 수신자 조회 실행: " + paramMap);

        Map<String, Object> result = imDao.getEmpName(paramMap);

        if (result == null || result.isEmpty()) {
            System.out.println("🚨 조회 결과 없음! receiverId: " + receiverId);
            return Collections.emptyMap();
        }

        System.out.println("✅ 수신자 정보 조회 성공: " + result);
        return result;
    }


    // 안읽은 메신저 개수
    @Override
    public int getUnreadMsg(Long empId) {
        return imDao.getUnreadMsg(Map.of("empId", empId));
    }

}