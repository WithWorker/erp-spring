package com.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberDto {
    private Long empId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String imgUrl;
    private String dept;
    private String position;
    private MemberRole memberRole;
}
