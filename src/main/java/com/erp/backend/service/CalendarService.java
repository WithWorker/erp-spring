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
        log.info("service-getAllCalendars");
        return calendarMapper.getAllCalendars();
    }

    public List<CalendarDto> getMyCalendars(Integer applicantId) {
        log.info("service-getMyCalendars");
        return calendarMapper.getMyCalendars(applicantId);
    }

    public List<CalendarDto> getDeptCalendars(String dept) {
        log.info("service-getDeptCalendars");
        return calendarMapper.getDeptCalendars(dept);  
    }
    
    public List<CalendarDto> getTodayCalendars() {
        log.info("service-getTodaysCalendars");
        return calendarMapper.getTodayCalendars();  
    }
    
    public CalendarDto readCalendar(Integer calendarId) {
        log.info("service-readCalendar");
        return calendarMapper.readCalendar(calendarId);
    }

    public int addCalendar(CalendarDto calendarDto) {
        int result = -1;
        log.info("service-addCalendar");
        result = calendarMapper.addCalendar(calendarDto);
        return result;
    }   

    public int updateCalendar(CalendarDto calendarDto) {
        int result = -1;
        log.info("service-updateCalendar");
        result = calendarMapper.updateCalendar(calendarDto);
        return result;
    }

    public int deleteCalendar(Integer calendarId) {
        int result = -1;
        log.info("service-deleteCalendar");
        result = calendarMapper.deleteCalendar(calendarId);
        return result;
    }
}
