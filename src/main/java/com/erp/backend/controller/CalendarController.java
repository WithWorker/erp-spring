package com.erp.backend.controller;

import com.erp.backend.dto.CalendarDto;
import com.erp.backend.service.CalendarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class CalendarController {
	private final CalendarService calendarService;

	// 사원 전체 일정 목록 
	// http://localhost:7777/calendar/all
	@GetMapping("/user/calendar/all")
	public List<CalendarDto> getAllCalendars() {
			return calendarService.getAllCalendars();
	}

	// 개인 일정 목록
	// http://localhost:7777/calendar/my/{applicantId}
	@GetMapping("/user/calendar/my/{applicantId}")
	public List<CalendarDto> getMyCalendars(@PathVariable Integer applicantId) {
		return calendarService.getMyCalendars(applicantId);
	}

	// 부서 일정 목록
	// http://localhost:7777/calendar/dept/{dept}
	@GetMapping("/user/calendar/dept/{departmentId}")
	public List<CalendarDto> getDeptCalendars(@PathVariable Long departmentId) {
		return calendarService.getDeptCalendars(departmentId);
	}

	// 오늘 일정 전체 목록
	// http://localhost:7777/calendar/today
	@GetMapping("/user/calendar/today")
	public List<CalendarDto> getTodayCalendars() {
			return calendarService.getTodayCalendars();
	}

	// 오늘 일정 개인 목록
	// http://localhost:7777/calendar/mytoday/{applicantId}
	@GetMapping("/user/calendar/mytoday/{applicantId}")
	public List<CalendarDto> getMyTodayCalendars(@PathVariable Integer applicantId) {
		return calendarService.getMyTodayCalendars(applicantId);
	}

	// 오늘 일정 부서 목록
	// http://localhost:7777/calendar/depttoday/{dept}
	@GetMapping("/user/calendar/depttoday/{departmentId}")
	public List<CalendarDto> getDeptTodayCalendars(@PathVariable Long departmentId) {
		return calendarService.getDeptTodayCalendars(departmentId);
	}

	//특정 일자 일정 목록
	@GetMapping("/user/calendar/date/{date}")
	public List<CalendarDto> getDateCalendars(@PathVariable("date") String date) {
		return calendarService.getDateCalendars(date);
	}

	// 일정 상세보기
	// http://localhost:7777/calendar/{calendarId}
	@GetMapping("/user/calendar/{calendarId}")
	public CalendarDto readCalendar(@PathVariable Integer calendarId) {
		return calendarService.readCalendar(calendarId);
	}

	// 일정 등록
	// http://localhost:7777/calendar/add
	@PostMapping("/user/calendar/add")
	public ResponseEntity<String> addCalendar(@RequestBody CalendarDto calendarDto) {
    log.info("Received CalendarDto: {}", calendarDto);
		System.out.println("applicant_id: " + calendarDto.getApplicantId()); // 확인용 출력
    calendarService.addCalendar(calendarDto);
    return ResponseEntity.ok("일정 등록 성공");
	}
	
	// 일정 수정
	// http://localhost:7777/calendar/edit/{calendarId}
	@PutMapping("/user/calendar/edit/{calendarId}")
	public ResponseEntity<String> updateCalendar(@PathVariable Integer calendarId, @RequestBody CalendarDto calendarDto) {
    calendarDto.setCalendarId(calendarId);
    calendarService.updateCalendar(calendarDto);
    return ResponseEntity.ok("일정 수정 성공");
	}
	
	// 일정 삭제
	// http://localhost:7777/calendar/{calendarId}
	@DeleteMapping("/user/calendar/{calendarId}")
	public ResponseEntity<String> deleteCalendar(@PathVariable Integer calendarId) {
		calendarService.deleteCalendar(calendarId);
		return ResponseEntity.ok("일정 삭제 성공");
	}
}
