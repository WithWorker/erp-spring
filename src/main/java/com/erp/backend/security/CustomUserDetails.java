package com.erp.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private final Long empId;

    public CustomUserDetails(Long empId, String username, String password, Collection<? extends GrantedAuthority> authorities ) {
        super(username, password, authorities);
        this.empId = empId;
    }

    public Long getEmpId() {
        return empId;
    }
}

