package com.erp.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.backend.dto.ApprovalDto;
import com.erp.backend.dto.MemberDto;
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

    @Transactional
public void addApproval(ApprovalDto approvalDto) {
    log.info("service-addApproval");
    // 1. 결재 신청 등록
    approvalMapper.addApproval(approvalDto);
    // 2. 생성된 approvalId를 사용하여 승인자 등록
    if (approvalDto.getApprovers() != null && !approvalDto.getApprovers().isEmpty()) {
        // MemberDto에서 empId만 추출하여 List<Integer>로 변환
        List<Long> approverIdsLong = approvalDto.getApprovers().stream()
                                                .map(MemberDto::getEmpId)  // MemberDto에서 empId만 추출
                                                .collect(Collectors.toList());
        // List<Long>을 List<Integer>로 변환
        List<Integer> approverIds = approverIdsLong.stream()
                                                   .map(Long::intValue)  // Long을 Integer로 변환
                                                    .collect(Collectors.toList());
        approvalMapper.addApprovers(approvalDto.getApprovalId(), approverIds); // List<Integer> 전달
    }
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
