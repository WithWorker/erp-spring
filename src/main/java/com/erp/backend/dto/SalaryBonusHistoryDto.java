package com.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryBonusHistoryDto {
    private Long empId;
    private LocalDate paymentDate;
    private Integer amount;
    private String type; // 'salary' or 'bonus'
}