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

    //결재 목록 조회 (by 신청자)
    public List<ApprovalDto> getApplicant(Integer applicantId) {
        log.info("service-getApplicant");
        return approvalMapper.getApplicant(applicantId);
    }

    //결재 목록 조회 (by 승인자)
    public List<ApprovalDto> getApprover(Integer approverId) {
        log.info("service-getApprover");
        return approvalMapper.getApprover(approverId);
    }

    //결재 목록 조회 (by 상태)
    public List<ApprovalDto> getApprovedList(Integer statusId) {
        log.info("service-getApprovedList");
        return approvalMapper.getApprovedList(statusId);
    }

    //결재 상세보기
    public ApprovalDto readApproval(Integer approvalId) {
        log.info("service-readApproval");
        return approvalMapper.readApproval(approvalId);
    }

    //결재 등록
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

    //결재 상태 변경
    @Transactional 
    public void updateStatus(ApprovalDto approvalDto) {
        log.info("service-updateApproval");
        // 승인자의 상태 업데이트
        for (MemberDto approver : approvalDto.getApprovers()) {
            log.info("승인자 ID: {}, 현재 상태: {}", approver.getEmpId(), approver.getApproverStatusId());
            approvalDto.setApproverId(approver.getEmpId().intValue());  // 각 승인자 ID 설정
            approvalDto.setApproverStatusId(approver.getApproverStatusId()); // 각 승인자 상태 설정
            approvalMapper.updateApproverStatus(approvalDto);  // 승인자 상태 업데이트
        }
        // DB에서 최신 승인자 상태를 조회
        List<MemberDto> currentApprovers = approvalMapper.readApproval(approvalDto.getApprovalId()).getApprovers();
        
        log.info("현재 승인자 상태 목록 (DB 최신 데이터):");
        for (MemberDto approver : currentApprovers) {
            log.info("승인자 ID: {}, 상태: {}", approver.getEmpId(), approver.getApproverStatusId());
        }
        
        // 모든 승인자가 승인(2)인지 확인
        boolean allApproversApproved = currentApprovers.stream()
                                    .allMatch(approver -> approver.getApproverStatusId() == 2);

        if (allApproversApproved) {
            log.info("모든 승인자가 승인 상태(2)입니다. approval.statusId를 2로 변경합니다.");
            // 승인자 모두 상태가 2라면 approval의 status_id를 2로 변경
            approvalDto.setStatusId(2);  // 상태를 승인 상태로 설정
            approvalMapper.updateApprovalStatus(approvalDto);  // approval의 상태 변경

            log.info("approval.statusId가 2로 업데이트됨 (approvalId: {})", approvalDto.getApprovalId());
    
            // 상태가 2로 변경되었으므로 캘린더에 자동으로 등록
            approvalMapper.insertCalendarFromApproval(approvalDto);
        }else {
            log.info("아직 모든 승인자가 승인 상태가 아님. approval.statusId 변경 없음.");
        }
        
        log.info("=== service-updateApproval 종료 ===");
    }

    //결재 삭제
    @Transactional
    public int deleteApproval(Integer approvalId) {
        int result = -1;
        log.info("service-deleteApproval");
        approvalMapper.deleteApprovers(approvalId);
        result = approvalMapper.deleteApproval(approvalId);
        return result;
    }
}
