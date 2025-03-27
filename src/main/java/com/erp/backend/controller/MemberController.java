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
    @GetMapping("/user/employees")
    public List<MemberDto> findAll() {
        return memberService.findAll();
    }

    //전체조회 (by 부서id)
    @GetMapping("/user/dept/{departmentId}")
    public List<MemberDto> findAllByDept(@PathVariable(value = "departmentId") Long departmentId) {
        return memberService.findAllByDept(departmentId);
    }

    //전체조회 (by 이름)
    @PostMapping("/user/name")
    public List<MemberDto> findByName(@RequestBody MemberDto memberDto) {
        return memberService.findByName(memberDto.getName());
    }

    //직원조회 (by 직원id)
    @GetMapping("/user/emp/{empId}")
    public MemberDto findById(@PathVariable(value = "empId") Long empId) {
        return memberService.findById(empId);
    }

    //직원등록
    @PostMapping("/admin/join")
    public ResponseEntity<String> insertMember(@RequestBody MemberDto memberDto) {
        try {
            memberService.insertMember(memberDto);
            return ResponseEntity.ok("success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //직원수정
    @PutMapping("/admin/update/{empId}")
    public String updateMember(@PathVariable(value = "empId") Long empId, @RequestBody Map<String, Object> updateInfo) {
        // 로그로 updateInfo 확인
        System.out.println("Update Info: " + updateInfo);
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
            Object deptIdObj = updateInfo.get("departmentId");
            memberDto.setDepartmentId(deptIdObj != null ? ((Number) deptIdObj).longValue() : null);
        }
        if (updateInfo.containsKey("positionId")) {
            Object posIdObj = updateInfo.get("positionId");
            memberDto.setPositionId(posIdObj != null ? ((Number) posIdObj).longValue() : null);
        }
        if (updateInfo.containsKey("baseSalary")) {
            Object salObj = updateInfo.get("baseSalary");
            memberDto.setBaseSalary(salObj != null ? ((Number) salObj).intValue() : null);
        }
        if (updateInfo.containsKey("address")) {
            Object addressObj = updateInfo.get("address");
            memberDto.setAddress(addressObj != null ? (String) addressObj : null);
        }

        if (updateInfo.containsKey("residentNumber")) {
            Object residentNumberObj = updateInfo.get("residentNumber");
            memberDto.setResidentNumber(residentNumberObj != null ? (String) residentNumberObj : null);
        }

        if (updateInfo.containsKey("accountNumber")) {
            Object accountNumberObj = updateInfo.get("accountNumber");
            memberDto.setAccountNumber(accountNumberObj != null ? (String) accountNumberObj : null);
        }

        memberService.updateMember(memberDto);
        return "success";
    }

    //부서이동
    @PutMapping("/update/department/{empId}")
    public String updateDepartment(@PathVariable Long empId, @RequestBody MemberDto memberDto) {
        memberDto.setEmpId(empId);
        memberService.updateDepartment(memberDto);
        return "success";
    }

    //직급변경
    @PutMapping("/update/position/{empId}")
    public String updatePosition(@PathVariable Long empId, @RequestBody MemberDto memberDto) {
        memberDto.setEmpId(empId);
        memberService.updatePosition(memberDto);
        return "success";
    }

    //퇴사
    @PutMapping("/admin/resign/{empId}")
    public String resignMember(@PathVariable(value = "empId") Long empId) {
        memberService.resignMember(empId);
        return "success";
    }

    //비밀번호 재설정
    @PutMapping("/find")
    public ResponseEntity<?> updatePassword(@RequestBody MemberDto memberDto) {
        try {
            memberService.updatePassword(memberDto);
            return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 성공적으로 변경되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    //직원삭제
    @DeleteMapping("/admin/delete/{empId}")
    public String deleteMember(@PathVariable(value = "empId") Long empId) {
        memberService.deleteMember(empId);
        return "success";
    }

    //성과급 조회
    @GetMapping("/admin/bonus/{empId}")
    public ResponseEntity<Integer> getBonus(@PathVariable Long empId) {
        Integer bonus = memberService.getBonusByEmpId(empId);
        return ResponseEntity.ok(bonus);
    }
}