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

    public List<CalendarDto> getAllCalendars() {
        log.info("service-전체조회 성공");
        return calendarMapper.getAllCalendars();
    }

    public List<CalendarDto> getMyCalendars(Integer applicantId) {
        log.info("service-개인조회 성공");
        return calendarMapper.getMyCalendars(applicantId);
    }

    public List<CalendarDto> getDeptCalendars(String dept) {
        log.info("service-부서조회 성공");
        return calendarMapper.getDeptCalendars(dept);  
    }
    
    public CalendarDto readCalendar(Integer calendarId) {
        log.info("service-상세보기 성공");
        return calendarMapper.readCalendar(calendarId);
    }

    public int addCalendar(CalendarDto calendarDto) {
        int result = -1;
        log.info("service-등록 성공");
        result = calendarMapper.addCalendar(calendarDto);
        return result;
    }   

    public int updateCalendar(CalendarDto calendarDto) {
        int result = -1;
        log.info("service-수정 성공");
        result = calendarMapper.updateCalendar(calendarDto);
        return result;
    }

    public int deleteCalendar(Integer calendarId) {
        int result = -1;
        log.info("service-삭제 성공");
        result = calendarMapper.deleteCalendar(calendarId);
        return result;
    }
}
