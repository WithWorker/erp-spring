package com.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long empId;
    private String name;
    private String email;
    private String password;
    private String repassword;
    private String phone;
    private String imgUrl;
    private Long departmentId;
    private String departmentName;
    private Long positionId;
    private String positionName;
    private Integer baseSalary;
    private Integer bonus;

    // approver 승인 상태 추가
    private Integer approverStatusId;
    private String approverStatusName;
    private String approverDepartmentName;
}