package com.erp.backend.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CalendarDto {
    private int calendarId;  
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String content;
    private int applicantId;
}