package com.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    private Long attendanceId;
    private Long empId;
    private LocalDateTime inTime;
    private LocalDateTime outTime;
}

