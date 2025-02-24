package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //사원등록
    @ResponseBody
    @PostMapping(value = "/insert")
    public String insertMember(@RequestBody MemberDto memberDto) {
        memberService.insertMember(memberDto);
        return "success";
    }

}
