package com.erp.backend.controller;

import com.erp.backend.dto.MemberDto;
import com.erp.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //전체조회
    @ResponseBody
    @GetMapping("/users")
    public List<MemberDto> findAll() {
        return memberService.findAll();
    }

    //사원조회 (by id)
    @ResponseBody
    @GetMapping("/{empId}")
    public MemberDto findByName(@PathVariable(value = "empId") Long empId) {
        return memberService.findById(empId);
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
    @PutMapping("/update/{empId}")
    public String updateMember(@PathVariable(value = "empId") Long empId, @RequestBody Map<String, Object> updateInfo) {
        MemberDto memberDto = memberService.findById(empId);

        if (updateInfo.containsKey("name")) {
            memberDto.setName((String) updateInfo.get("name"));
        }
        if (updateInfo.containsKey("email")) {
            memberDto.setEmail((String) updateInfo.get("email"));
        }
        if (updateInfo.containsKey("phone")) {
            memberDto.setPhone((String) updateInfo.get("phone"));
        }
        if (updateInfo.containsKey("departmentId")) {
            memberDto.setDepartmentId((Integer) updateInfo.get("departmentId"));
        }
        if (updateInfo.containsKey("positionId")) {
            memberDto.setPositionId((Integer) updateInfo.get("positionId"));
        }

        memberService.updateMember(memberDto);
        return "success";
    }

    //부서이동
    @ResponseBody
    @PutMapping("/update/department/{empId}")
    public String updateDepartment(@PathVariable(value = "empId") Long empId, @RequestBody Long departmentId) {
        memberService.updateDepartment(empId, departmentId);
        return "success";
    }

    //직급변경
    @ResponseBody
    @PutMapping("/update/position/{empId}")
    public String updatePosition(@PathVariable(value = "empId") Long empId, @RequestBody Long positionId) {
        memberService.updatePosition(empId, positionId);
        return "success";
    }

    //퇴사
    @ResponseBody
    @PutMapping("/resign/{empId}")
    public String resignMember(@PathVariable(value = "empId") Long empId) {
        memberService.resignMember(empId);
        return "success";
    }

    //비밀번호 재설정
    @ResponseBody
    @PutMapping("/find")
    public ResponseEntity<?> updatePassword(@RequestBody MemberDto memberDto) {
        try {
            memberService.updatePassword(memberDto);
            return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    //사원삭제
    @ResponseBody
    @DeleteMapping("/delete/{empId}")
    public String deleteMember(@PathVariable(value = "empId") Long empId) {
        memberService.deleteMember(empId);
        return "success";
    }
}