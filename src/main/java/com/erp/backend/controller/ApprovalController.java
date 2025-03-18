package com.erp.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erp.backend.dto.ApprovalDto;
import com.erp.backend.dto.MemberDto;
import com.erp.backend.service.ApprovalService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ApprovalController {
    private final ApprovalService approvalService;

    // 결재 신청 목록 : 신청자
    // http://localhost:7777/approval/applicant/{applicantId}
    @GetMapping("/approval/applicant/{applicantId}")
    public List<ApprovalDto> getApplicant(@PathVariable Integer applicantId){
        return approvalService.getApplicant(applicantId);
    }
    
    // 결재 신청 목록(대기) : 신청자
    // http://localhost:7777/approval/applicantPending/{applicantId}
    @GetMapping("/approval/applicantPending/{applicantId}")
    public List<ApprovalDto> getApplicantPending(@PathVariable Integer applicantId){
        return approvalService.getApplicantPending(applicantId);
    }
    
    // 결재 신청 목록(승인,반려) : 신청자
    // http://localhost:7777/approval/applicantApproved/{applicantId}
    @GetMapping("/approval/applicantApproved/{applicantId}")
    public List<ApprovalDto> getApplicantApproved(@PathVariable Integer applicantId){
        return approvalService.getApplicantApproved(applicantId);
    }

    // 결재 확인 목록 : 승인자 
    // http://localhost:7777/approval/approver/{approverId}
    @GetMapping("/approval/approver/{approverId}")
    public List<ApprovalDto> getApprover(@PathVariable Integer approverId){
        return approvalService.getApprover(approverId);
    }

    // 결재 상태 목록 
    // http://localhost:7777/approval/approved/{statusId}
    @GetMapping("/approval/approved/{statusId}")
    public List<ApprovalDto> getApprovedList(@PathVariable Integer statusId){
        return approvalService.getApprovedList(statusId);
    }

    // 결재 상세 조회 
    // http://localhost:7777/approval/{approvalId}
    @GetMapping("/approval/{approvalId}")
    public ApprovalDto readApproval(@PathVariable Integer approvalId){
        return approvalService.readApproval(approvalId);
    }

    // 승인자 검색
    // http://localhost:7777/approval/search/{keyword}
    @GetMapping("/approval/search/{keyword}")
    public List<MemberDto> searchApprover(@PathVariable String keyword){
        return approvalService.searchApprover(keyword);
    }

    // 결재 등록 : 신청자 
    // http://localhost:7777/approval/add
    @PostMapping("/approval/add")
	public ResponseEntity<Map<String, Object>> addApproval(@RequestBody ApprovalDto approvalDto) {
        log.info("Received ApprovalDto: {}", approvalDto);

        // 결재 등록 (신청자 + 승인자)
        approvalService.addApproval(approvalDto);

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("message", "결재 신청 완료");
        response.put("approvalId", approvalDto.getApprovalId()); // 생성된 결재 ID 반환

        return ResponseEntity.ok(response);
    }

    // 결재 수정(status) : 승인자 
    // http://localhost:7777/approval/edit/{approvalId}
    @PutMapping("/approval/edit/{approvalId}")
	public ResponseEntity<String> updateStatus(@PathVariable Integer approvalId, @RequestBody ApprovalDto approvalDto) {
        approvalDto.setApprovalId(approvalId);
        approvalService.updateStatus(approvalDto);
        return ResponseEntity.ok("결재 상태 수정");
    }
    
    // 결재 삭제 : 신청자 
    // http://localhost:7777/approval/{approvalId}
    @DeleteMapping("/approval/{approvalId}")
	public ResponseEntity<String> deleteApproval(@PathVariable Integer approvalId) {
		approvalService.deleteApproval(approvalId);
		return ResponseEntity.ok("결재 삭제");
	}
}