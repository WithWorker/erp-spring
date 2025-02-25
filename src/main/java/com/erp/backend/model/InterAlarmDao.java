package com.erp.backend.model;

import java.util.List;

public interface InterAlarmDao {

    void addAlarm(String sql); // 알람 추가

    // 알람 조회
    List<AlarmVO> getAlarmList(String empId);

    // 지난 알람 조회
    List<AlarmVO> getPastAlarmList(String empId);

    // 알람 읽기
    void readAlarm(String alarmId);

    // 전체 알람 읽기
    void readAllAlarm(String empId);

    // 안읽은 소식 개수
    String getUnreadAlarm(String empId);
}

/*



*/