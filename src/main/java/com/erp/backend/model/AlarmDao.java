package com.erp.backend.model;

import java.util.List;
import java.util.Map;

import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.SqlSessionTemplate;

@Mapper
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

    // 알람 추가하기
    @Override
    public void addAlarm(AlarmVO alarm) {
        sqlsession.insert("addAlarm", alarm);
    }

    // 알람 조회하기
    @Override
    public List<AlarmVO> getAlarmList(Long empId) {
        return sqlsession.selectList("getAlarmList", empId);
    }

    // 지난 알람 조회
    @Override
    public List<AlarmVO> getPastAlarmList(Long empId) {
        List<AlarmVO> alarmList = sqlsession.selectList("getPastAlarmList", empId);
        return alarmList;
    }

    // 모든 알람 읽기
    @Override
    public void readAllAlarm(Long empId) {
        sqlsession.update("readAllAlarm", empId);

    }

    // 안 읽은 소식 개수 알아오기
    @Override
    public String getUnreadAlarm(Long empId) {
        String n = sqlsession.selectOne("getUnreadAlarm", empId);
        return n;
    }

}
