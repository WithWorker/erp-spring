package com.erp.backend.service;

import com.erp.backend.model.AlarmVO;
import com.erp.backend.model.InterAlarmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class AlarmService implements InterAlarmService {

    @Autowired
    private InterAlarmDao alarmDao;

    @Override
    public void addAlarm(Map<String, String> alarmData) {
        AlarmVO alarm = new AlarmVO();
        alarm.setAlarmName("메신저"); // 고정값
        alarm.setAlarmDesc(alarmData.get("content")); // 메시지 내용
        alarm.setMessengerId(Long.parseLong(alarmData.get("messengerId")));
        alarm.setReceiverId(Long.parseLong(alarmData.get("receiverId"))); // 수신자
        alarm.setUrl("/chat/" + alarmData.get("messengerId")); // 채팅 링크
        alarm.setUrl2("/details/" + alarmData.get("messengerId")); // 상세 링크
        alarm.setCreatedAt(LocalDateTime.now());

        alarmDao.addAlarm(alarm);
    }

    // 알람 조회
    @Override
    public List<AlarmVO> getAlarmList(Long empId) {
        return alarmDao.getAlarmList(empId);
    }

    // 지난 알람 조회
    @Override
    public List<AlarmVO> getPastAlarmList(Long empId) {
        return alarmDao.getPastAlarmList(empId);
    }

    // 알람 읽기
    @Override
    public boolean readAlarm(Long alarmId, Long empId) {
        int count = alarmDao.readAlarm(alarmId, empId);
        return count > 0;
    }

    // 모든 알람 읽기
    @Override
    public void readAllAlarm(Long empId) {
        alarmDao.readAllAlarm(empId);
    }

    // 안읽은 소식 개수
    @Override
    public String getUnreadAlarm(Long alarmId) {
        return alarmDao.getUnreadAlarm(alarmId);
    }
}
