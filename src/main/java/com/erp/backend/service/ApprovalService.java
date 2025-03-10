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
            // MemberDto에서 empId만 추출하여 List<Long>을 List<Integer>로 변환
            List<Integer> approverIds = approvalDto.getApprovers().stream()
                                        .map(approver -> approver.getEmpId().intValue())  // Long을 Integer로 변환
                                        .collect(Collectors.toList());
            // approverId들을 DB에 저장
            approvalMapper.addApprovers(approvalDto.getApprovalId(), approverIds);
        }
    }


    //@Transactional // 하나의 트랜잭션으로 묶기
    public int updateStatus(ApprovalDto approvalDto) {
        log.info("service-updateApproval");
        for (MemberDto approver : approvalDto.getApprovers()) {
            approvalDto.setApproverId(approver.getEmpId().intValue());  // 각 승인자 ID 설정
            approvalDto.setApproverStatusId(approver.getApproverStatusId()); // 각 승인자 상태 설정
            approvalMapper.updateApproverStatus(approvalDto);  // 승인자 상태 업데이트
        }
    
        return 1;  // 성공적으로 처리되었다면 1 반환

        // 상태 변경 (UPDATE)
        //int result = approvalMapper.updateStatus(approvalDto);
        // 승인(statusId == 2)이면 calendar에 추가 (INSERT)
        //if (approvalDto.getStatusId() == 2) {
        //    approvalMapper.insertCalendarFromApproval(approvalDto);
        //}     
        //return approvalDto;
    }

    @Transactional
    
    public int deleteApproval(Integer approvalId) {
        int result = -1;
        log.info("service-deleteApproval");
        approvalMapper.deleteApprovers(approvalId);
        result = approvalMapper.deleteApproval(approvalId);
        return result;
    }
}
