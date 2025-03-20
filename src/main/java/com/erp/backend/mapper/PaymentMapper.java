package com.erp.backend.mapper;

import com.erp.backend.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PaymentMapper {

    //성과급 수정
    void updateBonus(MemberDto memberDto);

    //급여내역 저장
    void insertPaymentHistory(Map<String, Object> insertInfo);
}
