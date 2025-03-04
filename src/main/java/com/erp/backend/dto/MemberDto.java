package com.erp.backend.dto;

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
    private Integer departmentId;
    private String departmentName;
    private Integer positionId;
    private String positionName;
    private MemberRole memberRole;
}


