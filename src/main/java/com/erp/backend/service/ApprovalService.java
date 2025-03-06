package com.erp.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ApprovalDto> getApprovedList(Integer statusId) {
        log.info("service-getApprovedList");
        return approvalMapper.getApprovedList(statusId);
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

    @Transactional // 하나의 트랜잭션으로 묶기
    public ApprovalDto updateApproval(ApprovalDto approvalDto) {
        log.info("service-updateApproval");
        // 상태 변경 (UPDATE)
        int result = approvalMapper.updateStatus(approvalDto);
        // 승인(statusId == 2)이면 calendar에 추가 (INSERT)
        if (approvalDto.getStatusId() == 2) {
            approvalMapper.insertCalendarFromApproval(approvalDto);
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
