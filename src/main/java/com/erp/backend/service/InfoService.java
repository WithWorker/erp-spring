package com.erp.backend.service;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.mapper.InfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoMapper infoMapper;

    //프로필 조회
    public MemberDto profile(Long empId){
        return infoMapper.profile(empId);
    }

}
