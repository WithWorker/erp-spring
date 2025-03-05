package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;

    //프로필 조회
    @ResponseBody
    @GetMapping("/info/{empId}")
    public MemberDto profile(@PathVariable(value = "empId") Long empId){
        return infoService.profile(empId);
    }

}
