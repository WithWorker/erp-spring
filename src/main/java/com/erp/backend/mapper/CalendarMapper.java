package com.erp.backend.mapper;

import com.erp.backend.dto.CalendarDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {

    //전체 일정 조회
    List<CalendarDto> getAllCalendars();

    //개인 일정 조회
    List<CalendarDto> getMyCalendars(Integer applicantId);

    //부서 일정 조회
    List<CalendarDto> getDeptCalendars(Long departmentId);

    //오늘 일정 조회
    List<CalendarDto> getTodayCalendars();

    //오늘 일정 조회 (by 직원id) 
    List<CalendarDto> getMyTodayCalendars(Integer applicantId);

    //오늘 일정 조회 (by 부서id)
    List<CalendarDto> getDeptTodayCalendars(Long departmentId);

    //오늘 일정 조회 (by 일자)
    List<CalendarDto> getDateCalendars(String date);

    //일정 상세조회
    CalendarDto readCalendar(Integer calendarId);

    //일정 추가
    int addCalendar(CalendarDto calendarDto);

    //일정 수정
    int updateCalendar(CalendarDto calendarDto);

    //일정 삭제
    int deleteCalendar(Integer calendarId);
}
