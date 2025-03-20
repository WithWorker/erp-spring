package com.erp.backend.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CustomUserDetails extends User {
    private final Long empId;

    public CustomUserDetails(String username, String password, String role, Long empId) {
        super(username, password, AuthorityUtils.createAuthorityList(role));
        this.empId = empId;
    }

    public Long getEmpId() {
        return empId;
    }
}

