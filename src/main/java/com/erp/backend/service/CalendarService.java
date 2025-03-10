package com.erp.backend.service;

import com.erp.backend.dto.CalendarDto;
import com.erp.backend.mapper.CalendarMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarMapper calendarMapper;
    
    //전체 일정 조회
    public List<CalendarDto> getAllCalendars() {
        log.info("service-getAllCalendars");
        return calendarMapper.getAllCalendars();
    }

    //개인 일정 조회
    public List<CalendarDto> getMyCalendars(Integer applicantId) {
        log.info("service-getMyCalendars");
        return calendarMapper.getMyCalendars(applicantId);
    }

    //부서 일정 조회
    public List<CalendarDto> getDeptCalendars(Long departmentId) {
        log.info("service-getDeptCalendars");
        return calendarMapper.getDeptCalendars(departmentId);  
    }
    
    //오늘 일정 조회
    public List<CalendarDto> getTodayCalendars() {
        log.info("service-getTodaysCalendars");
        return calendarMapper.getTodayCalendars();  
    }

    //오늘 일정 조회 (by 직원id)
    public List<CalendarDto> getMyTodayCalendars(Integer applicantId) {
        log.info("service-getMyTodayCalendars");
        return calendarMapper.getMyTodayCalendars(applicantId);
    }

    //오늘 일정 조회 (by 부서id)
    public List<CalendarDto> getDeptTodayCalendars(Long departmentId) {
        log.info("service-getDeptTodayCalendars");
        return calendarMapper.getDeptTodayCalendars(departmentId);  
    }
    
    //오늘 일정 조회 (by 일자)
    public List<CalendarDto> getDateCalendars(String date) {
        log.info("service-getDateCalendars");
        return calendarMapper.getDateCalendars(date);
    }

    //일정 상세보기
    public CalendarDto readCalendar(Integer calendarId) {
        log.info("service-readCalendar");
        return calendarMapper.readCalendar(calendarId);
    }

    //일정 등록
    public int addCalendar(CalendarDto calendarDto) {
        int result = -1;
        log.info("service-addCalendar");
        result = calendarMapper.addCalendar(calendarDto);
        return result;
    }   

    //일정 수정
    public int updateCalendar(CalendarDto calendarDto) {
        int result = -1;
        log.info("service-updateCalendar");
        result = calendarMapper.updateCalendar(calendarDto);
        return result;
    }

    //일정 삭제
    public int deleteCalendar(Integer calendarId) {
        int result = -1;
        log.info("service-deleteCalendar");
        result = calendarMapper.deleteCalendar(calendarId);
        return result;
    }
}
