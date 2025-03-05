package com.erp.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.erp.backend.dto.ApprovalDto;
import com.erp.backend.dto.CalendarDto;
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
    // 결재 확인 목록 : 승인자 
    // http://localhost:7777/approval/approver/{approverId}
    @GetMapping("/approval/approver/{approverId}")
    public List<ApprovalDto> getApprover(@PathVariable Integer approverId){
        return approvalService.getApprover(approverId);
    }
    // 결재 승인 목록 
    // http://localhost:7777/approval/approved/{statusId}
    @GetMapping("/approval/approved")
    public List<ApprovalDto> getApprovedList(){
        return approvalService.getApprovedList();
    }
    // 결재 상세 조회 
    // http://localhost:7777/approval/{approvalId}
    @GetMapping("/approval/{approvalId}")
    public ApprovalDto readApproval(@PathVariable Integer approvalId){
        return approvalService.readApproval(approvalId);
    }
    // 결재 등록 : 신청자 
    // http://localhost:7777/approval/add
    @PostMapping("/approval/add")
	public ResponseEntity<String> addApproval(@RequestBody ApprovalDto approvalDto) {
        log.info("Received ApprovalDto: {}", approvalDto);
        approvalService.addApproval(approvalDto);
        return ResponseEntity.ok("결재 신청");
    }
    // 결재 수정(status) : 승인자 
    // http://localhost:7777/approval/edit/{approvalId}
    @PutMapping("/approval/edit/{approvalId}")
	public ResponseEntity<ApprovalDto> updateApproval(@PathVariable Integer approvalId, @RequestBody ApprovalDto approvalDto) {
        approvalDto.setApprovalId(approvalId);
        // 결재 수정 서비스 호출
        ApprovalDto updatedApproval = approvalService.updateApproval(approvalDto);
        // 결재 상태 수정 후 calendarDto 포함된 updatedApproval 반환
        return ResponseEntity.ok(updatedApproval);
    }
    // 결재 삭제 : 신청자 
    // http://localhost:7777/approval/{approvalId}
    @DeleteMapping("/approval/{approvalId}")
	public ResponseEntity<String> deleteApproval(@PathVariable Integer approvalId) {
		approvalService.deleteApproval(approvalId);
		return ResponseEntity.ok("결재 삭제");
	}
}