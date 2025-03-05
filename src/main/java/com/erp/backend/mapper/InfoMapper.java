package com.erp.backend.mapper;

import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InfoMapper {

    //프로필 조회
    MemberDto profile(Long empId);

}
