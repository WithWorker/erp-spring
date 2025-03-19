package com.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    private Long empId;
    private String date;   // 날짜 필드 추가 (월 단위 조회용)
    private String inTime;
    private String outTime;
}
