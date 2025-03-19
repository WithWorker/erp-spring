package com.erp.backend.mapper;

import com.erp.backend.dto.AttendanceDto;
import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InfoMapper {

    //프로필 조회
    MemberDto profile(MemberDto memberDto);

    //급여 조회
    List<PaymentDto> getPaymentHistory(Long empId, int year, int month);

    //근태 조회
    AttendanceDto attendance(Long empId, String date);

    //출근 확인
    boolean hasTodayCheckIn(Long empId);

    //출근 업데이트
    void updateTodayCheckIn(Long empId);

    //출근 기록
    void insertCheckIn(Long empId);

    //퇴근 기록
    void updateCheckOut(Long empId);
}

