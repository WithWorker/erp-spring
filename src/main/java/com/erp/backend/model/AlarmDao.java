package com.erp.backend.model;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AlarmDao implements InterAlarmDao {

    @Resource
    private SqlSessionTemplate sqlsession;

    // 알람 소유자 ID 조회
    @Override
    public String getAlarmOwnerId(Long empId) {
        return sqlsession.selectOne("selectAlarmOwnerId", empId);
    }

    // 알람 읽기 처리
    @Override
    public int readAlarm(Long alarmId, Long empId) {
        return sqlsession.update("updateAlarmReadStatus", Map.of("alarmId", alarmId, "empId", empId));
    }

    // AOP 알람추가하기
    @Override
    public void addAlarm(String sql) {
        sqlsession.insert("insertAlarm", sql);
    }

    // 알람 조회하기
    @Override
    public List<AlarmVO> getAlarmList(Long empId) {
        List<AlarmVO> alarmList = sqlsession.selectList("selectAlarm", empId);
        return alarmList;
    }

    // 지난 알람 조회
    @Override
    public List<AlarmVO> getPastAlarmList(Long empId) {
        List<AlarmVO> alarmList = sqlsession.selectList("selectPastAlarm", empId);
        return alarmList;
    }

    // 모든 알람 읽기
    @Override
    public void readAllAlarm(Long empId) {
        sqlsession.update("updateAllAlarm", empId);

    }

    // 안 읽은 소식 개수 알아오기
    @Override
    public String getUnreadAlarm(Long empId) {
        String n = sqlsession.selectOne("getUnreadAlarm", empId);
        return n;
    }

}
