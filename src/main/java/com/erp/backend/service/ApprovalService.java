package com.erp.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.backend.dto.ApprovalDto;
import com.erp.backend.dto.CalendarDto;
import com.erp.backend.mapper.ApprovalMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ApprovalService {
    private final ApprovalMapper approvalMapper;

    public List<ApprovalDto> getApplicant(Integer applicantId) {
        log.info("service-getApplicant");
        return approvalMapper.getApplicant(applicantId);
    }
    public List<ApprovalDto> getApprover(Integer approverId) {
        log.info("service-getApprover");
        return approvalMapper.getApprover(approverId);
    }
    public List<ApprovalDto> getApprovedList() {
        log.info("service-getApprovedList");
        return approvalMapper.getApprovedList();
    }
    public ApprovalDto readApproval(Integer approvalId) {
        log.info("service-readApproval");
        return approvalMapper.readApproval(approvalId);
    }

    public int addApproval(ApprovalDto approvalDto) {
        int result = -1;
        log.info("service-addApproval");
        result = approvalMapper.addApproval(approvalDto);
        return result;
    }   

    public ApprovalDto updateApproval(ApprovalDto approvalDto) {
    // 1. 결재 상태 업데이트
    approvalMapper.updateApproval(approvalDto);
    // 2. 상태가 "승인(2)"으로 변경된 경우에만 calendar 정보 조회
    if ("2".equals(approvalDto.getStatusId())) {
        CalendarDto calendarDto = approvalMapper.getCalendarByApprovalId(approvalDto.getApprovalId());
        approvalDto.setCalendarDto(calendarDto); // ApprovalDto에 Calendar 정보 추가
    }

    return approvalDto;
    }

    public int deleteApproval(Integer approvalId) {
        int result = -1;
        log.info("service-deleteApproval");
        result = approvalMapper.deleteApproval(approvalId);
        return result;
    }
}
