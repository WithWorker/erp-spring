package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //전체조회
    @ResponseBody
    @GetMapping("/users")
    public List<MemberDto> findAll() {
        return memberService.findAll();
    }

    //사원조회 (by 이름)
    @ResponseBody
    @GetMapping("/{name}")
    public MemberDto findByName(@PathVariable(value = "{name}") String name) {
        return memberService.findByName(name);
    }

    //사원등록
    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> insertMember(@RequestBody MemberDto memberDto) {
        try {
            memberService.insertMember(memberDto);
            return ResponseEntity.ok("success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //사원수정
    @ResponseBody
    @PutMapping("/edit/{empId}")
    public String updateMember(@PathVariable(value = "empId") Long empId, @RequestBody Map<String, String> updateInfo) {
        MemberDto memberDto = memberService.findById(empId);
        memberDto.setName(updateInfo.get("name"));
        memberDto.setEmail(updateInfo.get("email"));
        memberDto.setPhone(updateInfo.get("phone"));
        memberDto.setDept(updateInfo.get("dept"));
        memberDto.setPosition(updateInfo.get("position"));
        memberService.updateMember(memberDto);
        return "success";
    }

    //사원삭제
    @ResponseBody
    @DeleteMapping("/delete/{empId}")
    public String deleteMember(@PathVariable(value = "empId") Long empId) {
        memberService.deleteMember(empId);
        return "success";
    }
}