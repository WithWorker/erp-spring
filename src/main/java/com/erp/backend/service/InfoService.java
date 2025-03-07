package com.erp.backend.service;

import com.erp.backend.dto.AttendanceDto;
import com.erp.backend.dto.MemberDto;
import com.erp.backend.dto.SalaryBonusHistoryDto;
import com.erp.backend.mapper.InfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoMapper infoMapper;

    //프로필 조회
    public MemberDto profile(Long empId) {
        return infoMapper.profile(empId);
    }

    //급여 조회
    public List<SalaryBonusHistoryDto> getSalaryAndBonusHistory(Long empId, int year, int month) {
        return infoMapper.salaryAndBonusHistory(empId, year, month);
    }

    //근태 조회
    public AttendanceDto attendance(Long empId) {
        return infoMapper.attendance(empId);
    }

    //출근 기록
    public void checkIn(Long empId) {
        if (infoMapper.hasTodayCheckIn(empId)) {
            infoMapper.updateTodayCheckIn(empId);
        } else {
            infoMapper.insertCheckIn(empId);
        }
    }

    //퇴근 기록
    public void checkOut(Long empId) {
        infoMapper.updateCheckOut(empId);
    }
}

