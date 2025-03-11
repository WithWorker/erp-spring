package com.erp.backend.service;

import com.erp.backend.model.AlarmVO;

import java.util.Map;
import java.util.List;

public interface InterAlarmService {

    void addAlarm(Map<String, String> map);

    // 알람 조회
    List<AlarmVO> getAlarmList(Long empId);

    // 지난 알람 조회
    List<AlarmVO> getPastAlarmList(Long empId);

    // 알람 읽기
    boolean readAlarm(Long alarmId, Long empId);

    // 모든 알람 읽기
    void readAllAlarm(Long empId);

    // 안읽은 알람 개수
    String getUnreadAlarm(Long empId);
}
