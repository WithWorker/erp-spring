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
    private String phone;
    private String imgUrl;
    private String dept;
    private String position;
    
    private Integer approverStatusId;
    private String approverStatusName;
}