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
    private MemberRole memberRole;
    private LocalDate hireDate;
    private LocalDate resignDate;
    private Integer baseSalary;
    private Integer departmentBonus;
    // approver 승인 상태 추가
    private Integer approverStatusId;
    private String approverStatusName;
    private String approverDepartmentName;
}
