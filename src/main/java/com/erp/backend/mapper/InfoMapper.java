package com.erp.backend.mapper;

import com.erp.backend.dto.AttendanceDto;
import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoMapper {

    //프로필 조회
    MemberDto profile(Long empId);

    //근태 조회
    AttendanceDto attendance(Long empId);

    //출근 확인
    boolean hasTodayCheckIn(Long empId);

    //출근 업데이트
    void updateTodayCheckIn(Long empId);

    //출근 기록
    void insertCheckIn(Long empId);

    //퇴근 기록
    void updateCheckOut(Long empId);
}

