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
        List<Map<String, String>> deptList = imDao.getDept();
        return deptList;
    }

    // 부서 직원 조회
    @Override
    public List<Map<String, String>> getDeptPerson(Map<String, String> map) {
        List<Map<String, String>> deptPerson = imDao.getDeptPerson(map);
        return deptPerson;
    }

    // 해당 부서 팀 조회 (20250219)
    @Override
    public List<Map<String, String>> getTeam(String dept) {
        List<Map<String, String>> team = imDao.getTeam(dept);
        return team;
    }

    // 선택 직원 가져오기
    @Override
    public List<Map<String, String>> getChosenEmp(String empId) {
        List<Map<String, String>> chosenEmp = imDao.getChosenEmp(empId);
        return chosenEmp;
    }

    // 메신저 보내기
    @Override
    public String sendMessage(MessengerVO mvo) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String time = sdf.format(calendar.getTime());

        String receiverId = mvo.getReceiverId();
        String[] arr_receiverId = receiverId.split(",");

        StringBuilder sb = new StringBuilder();
        sb.append("insert all");

        if(mvo.getMessengerId() == "" || mvo.getMessengerId() == null) {
            mvo.setReceiverId("null");
        }

        String start = " into messenger(group_id, filepath, messenger_id, sender_id, receiver_id, content) values("+ time + ", " + mvo.getFilePath();
        String end = ", '" + mvo.getMessengerId() + "', '" + mvo.getContent() + "')";
        // messenger table에 filepath 추가

        for(int i = 0; i < arr_receiverId.length; i++) {
            sb.append(start);
            sb.append(time + i + ", " + mvo.getSenderId() + ", " + arr_receiverId[i]);
            sb.append(end);
        }

        sb.append("select * from dual");
        imDao.sendMessage(sb.toString());

        return time;
    }

    // 보낸 메일 리스트
    @Override
    public List<Map<String, String>> getSendMsg(Map<String, String> map) {
        List<Map<String, String>> sendMsg = imDao.getSendMsg(map);
        return sendMsg;
    }

    // 받은 메일 리스트
    @Override
    public List<Map<String, String>> getReceivedMsg(Map<String, String> map) {
        List<Map<String, String>> receiveMsg = imDao.getReceivedMsg(map);
        return receiveMsg;
    }

    // 보낸 메신저 내용 조회
    @Override
    public Map<String, String> getMsgContent(String content) {
        Map<String, String> MsgContent = imDao.getMsgContent(content);
        return MsgContent;
    }

    // 받은 메신저 내용 조회
    @Override
    public Map<String, String> getMsgContent2(String content) {
        Map<String, String> MsgContent = imDao.getMsgContent2(content);
        return MsgContent;
    }

    // 안읽은 메신저 읽기
    @Override
    public void updateAllMsg(String empId) {
        imDao.updateAllMsg(empId);
    }

    // 메신저 첨부파일 추가
    @Override
    public void addFile(FileVO filevo) {
        imDao.addFile(filevo);
    }

    // 첨부파일 조회
    @Override
    public List<FileVO> getMsgFile(String messengerId) {
        List<FileVO> msgFile = imDao.getMsgFile(messengerId);
        return msgFile;
    }

    // 메신저 전달
    @Override
    public void deliverMessage(MessengerVO dvo, MessengerVO mvo) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(calendar.getTime());

        String receiverId = dvo.getReceiverId();
        String[] arr_receiverId = receiverId.split(",");

        StringBuilder sb = new StringBuilder();
        sb.append("insert all");

        String start = " into messenger(group_id, filepath, messenger_id, sender_id, receiver_id, content) values("+ dvo.getMessengerId() + ", " + dvo.getFilePath() + ",";
        String end = ", null , '" + mvo.getContent() + "')";

        for(int i = 0; i < arr_receiverId.length; i++) {
            sb.append(start);
            sb.append(time + i + ", " + mvo.getSenderId() + ", " + arr_receiverId[i]);
            sb.append(end);
        }

        sb.append("select * from dual");
        imDao.sendMessage(sb.toString());
    }

    // 메신저 방 개수
    @Override
    public int getTotalMsg(Map<String, String> map) {
        int totalMsg = imDao.getTotalMsg(map);
        return totalMsg;
    }

    // 메시지 발송을 위한 사람 이름 조회
    @Override
    public String getEmpName(String empId) {
        String name = imDao.getEmpName(empId);
        return name;
    }

    // 안읽은 메신저 개수
    @Override
    public int getUnreadMsg(String empId) {
        int unreadMsg = imDao.getUnreadMsg(empId);
        return unreadMsg;
    }
}
