package com.erp.backend.service;

import com.erp.backend.model.AlarmVO;

import java.util.Map;
import java.util.List;

public interface InterAlarmService {

    void addAlarm(Map<String, String> map);

    // 알람 조회
    List<AlarmVO> getAlarmList(String empId);

    // 지난 알람 조회
    List<AlarmVO> getPastAlarmList(String empId);

    // 알람 읽기
    void readAlarm(String alarmId);

    // 모든 알람 읽기
    void readAllAlarm(String empId);

    // 안읽은 알람 개수
    String getUnreadAlarm(String empId);
}
