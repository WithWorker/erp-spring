package com.erp.backend.mapper;

import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    void insertMember(MemberDto memberDto);
}

