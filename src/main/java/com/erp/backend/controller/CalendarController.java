package com.erp.backend.controller;

import com.erp.backend.dto.CalendarDto;
import com.erp.backend.service.CalendarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/calendar/all")
    public List<CalendarDto> getAllCalendars() {
        return calendarService.getAllCalendars();
    }
    
    @PostMapping("/calendar/add")
    public void addCalendar(CalendarDto calendarDto) {
        log.info("Controller-등록");
        calendarService.addCalendar(calendarDto);
    }

}
