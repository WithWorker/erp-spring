package com.erp.backend.model;

import java.util.List;
import jakarta.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AlarmDao implements InterAlarmDao {

    @Resource
    private SqlSessionTemplate sqlsession;

    // AOP 알람추가하기
    @Override
    public void addAlarm(String sql) {
        sqlsession.insert("insertAlarm", sql);
    }

    // 알람 조회하기
    @Override
    public List<AlarmVO> getAlarmList(String empId) {
        List<AlarmVO> alarmList = sqlsession.selectList("selectAlarm", empId);
        return alarmList;
    }

    // 지난 알람 조회
    @Override
    public List<AlarmVO> getPastAlarmList(String empId) {
        List<AlarmVO> alarmList = sqlsession.selectList("selectPastAlarm", empId);
        return alarmList;
    }

    // 알람 읽기
    @Override
    public void readAlarm(String alarmId) {
        sqlsession.update("updateAlarm", alarmId);
    }

    // 모든 알람 읽기
    @Override
    public void readAllAlarm(String empId) {
        sqlsession.update("updateAllAlarm", empId);

    }

    // 안 읽은 소식 개수 알아오기
    @Override
    public String getUnreadAlarm(String empId) {
        String n = sqlsession.selectOne("getUnreadAlarm", empId);
        return n;
    }

}
