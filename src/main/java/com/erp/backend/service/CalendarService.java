package com.erp.backend.service;

import com.erp.backend.dto.CalendarDto;
import com.erp.backend.mapper.CalendarMapper;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CalendarService {
    private CalendarMapper calendarMapper;

    @Autowired
    public CalendarService(CalendarMapper calendarMapper) {
        this.calendarMapper = calendarMapper;
    }

    public List<CalendarDto> getAllCalendars() {
        log.info("service 호출 성공");
        return calendarMapper.getAllCalendars();
    }
}
