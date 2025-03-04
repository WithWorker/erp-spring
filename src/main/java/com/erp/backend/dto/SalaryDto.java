package com.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDto {
    private Long salaryId;
    private Long empId;
    private Long positionId;
    private Long departmentId;
    private Integer base;
    private Integer bonus;
    private Integer total;
}

