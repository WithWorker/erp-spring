package com.erp.backend.mapper;

import com.erp.backend.dto.CalendarDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {
    List<CalendarDto> getAllCalendars();
    List<CalendarDto> getMyCalendars(Integer applicantId);
    CalendarDto readCalendar(Integer calendarId);
    int addCalendar(CalendarDto calendarDto);
    int updateCalendar(CalendarDto calendarDto);
    int deleteCalendar(Integer calendarId);
    List<CalendarDto> getDeptCalendars(String dept);
}
