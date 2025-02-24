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
        log.info("service 호출 성공");
        return calendarMapper.getAllCalendars();
    }
}
