package com.erp.backend.service;

import com.erp.backend.model.AlarmVO;
import com.erp.backend.model.InterAlarmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class AlarmService implements InterAlarmService {

    @Autowired
    private InterAlarmDao alarmDao;

    @Override
    public void addAlarm(Map<String, String> map) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String date = sdf.format(calendar.getTime());
        String receiverId = map.get("receiverId");

        if(receiverId == null || receiverId == "") {
            return;
        }

        String url = map.get("url");
        String[] arr_receiverId = receiverId.split(",");
        String[] arr_url = url.split(",");

        String alarmType = "";

        // 알람 유형
        if( "1".equals(map.get("alarm_type"))) alarmType = ""; // 중요 알림
        if( "2".equals(map.get("alarm_type"))) alarmType = ""; // 휴가 관련
        if( "3".equals(map.get("alarm_type"))) alarmType = ""; // 메신저
        if( "4".equals(map.get("alarm_type"))) alarmType = ""; // 캘린더
        if( "5".equals(map.get("alarm_type"))) alarmType = ""; // 출근 (로그인)
        if( "6".equals(map.get("alarm_type"))) alarmType = ""; // 퇴근 (로그아웃)

        StringBuilder sb = new StringBuilder();
        sb.append(" insert all ");

        String start = " into messenger_alarm (alarm_id, receiver_id, url, url2, alarm_type) values (";
        String end = map.get("url2") + "', '" + map.get("alarmDesc") + "', '" + alarmType + "', '" + date + "')";

        if(arr_url.length > 1) {
            for(int i = 0; i < arr_receiverId.length; i++) {
                sb.append(start);
                sb.append(date + i + " , " + arr_receiverId[i] + " , '" + arr_url[i] + "', '");
                sb.append(end);
            }
        } else {
            for(int i = 0; i < arr_receiverId.length; i++) {
                sb.append(start);
                sb.append(date + i + " , " + arr_receiverId[i] + " , '" + url + "', '");
                sb.append(end);
            }
        }

        sb.append("select * from dual");
        alarmDao.addAlarm(sb.toString());
    }

    // 알람 조회
    @Override
    public List<AlarmVO> getAlarmList(String empId) {
        List<AlarmVO> alarmList = alarmDao.getAlarmList(empId);
        return alarmList;
    }

    // 지난 알람 조회
    @Override
    public List<AlarmVO> getPastAlarmList(String empId) {
        List<AlarmVO> alarmList = alarmDao.getPastAlarmList(empId);
        return alarmList;
    }

    // 알람 읽기
    @Override
    public boolean readAlarm(String alarmId, String empId) {
        int count = alarmDao.readAlarm(alarmId, empId);
        return count > 0;
    }

    // 모든 알람 읽기
    @Override
    public void readAllAlarm(String empId) {
        alarmDao.readAllAlarm(empId);
    }

    // 안읽은 소식 개수
    @Override
    public String getUnreadAlarm(String alarmId) {
        return alarmDao.getUnreadAlarm(alarmId);
    }
}
