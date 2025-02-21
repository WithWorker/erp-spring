package com.erp.backend.controller;

import com.erp.backend.dto.CalendarDto;
import com.erp.backend.service.CalendarService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController

public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/calendar")
    public List<CalendarDto> getAllCalendars() {
        log.info("controller 호출 성공");
        return calendarService.getAllCalendars();
    }
}
