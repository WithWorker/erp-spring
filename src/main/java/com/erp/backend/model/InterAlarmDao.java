package com.erp.backend.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface InterAlarmDao {

    // 알람 소유자 조회
    String getAlarmOwnerId(Long alarmId);

    // 알람 읽음 처리
    int readAlarm(Long alarmId, Long empId);

    // 알람 추가
    void addAlarm(String sql);

    // 알람 조회
    List<AlarmVO> getAlarmList(Long empId);

    // 지난 알람 조회
    List<AlarmVO> getPastAlarmList(Long empId);

    // 전체 알람 읽기
    void readAllAlarm(Long empId);

    // 안읽은 소식 개수
    String getUnreadAlarm(Long empId);
}

/*



*/