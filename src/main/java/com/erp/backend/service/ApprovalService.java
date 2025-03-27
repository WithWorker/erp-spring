package com.erp.backend.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.backend.dto.ApprovalDto;
import com.erp.backend.dto.DepartmentWithEmployeesDto;
import com.erp.backend.dto.MemberDto;
import com.erp.backend.mapper.ApprovalMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ApprovalService {
  private final ApprovalMapper approvalMapper;

  // 결재 목록 조회 (by 신청자)
  public List<ApprovalDto> getApplicant(Integer applicantId) {
    log.info("service-getApplicant");
    return approvalMapper.getApplicant(applicantId);
  }

  // 결재 대기 목록 조회 (by 신청자)
  public List<ApprovalDto> getApplicantPending(Integer applicantId) {
    log.info("service-getApplicantPending");
    return approvalMapper.getApplicantPending(applicantId);
  }

  // 결재 승인,반려 목록 조회 (by 신청자)
  public List<ApprovalDto> getApplicantApproved(Integer applicantId) {
    log.info("service-getApplicantApproved");
    return approvalMapper.getApplicantApproved(applicantId);
  }

  // 결재 목록 조회 (by 승인자)
  public List<ApprovalDto> getApprover(Integer approverId) {
    log.info("service-getApprover");
    return approvalMapper.getApprover(approverId);
  }

  // 결재 목록 조회 (by 상태)
  public List<ApprovalDto> getApprovedList(Integer statusId) {
    log.info("service-getApprovedList");
    return approvalMapper.getApprovedList(statusId);
  }

  // 결재 상세보기
  public ApprovalDto readApproval(Integer approvalId) {
    log.info("service-readApproval");
    return approvalMapper.readApproval(approvalId);
  }

  // 승인자 검색
  public List<MemberDto> searchApprover(String keyword) {
    log.info("service-searchApprover");
    return approvalMapper.searchApprover(keyword);
  }

  // 승인자 조직도
  public List<DepartmentWithEmployeesDto> getOrganization() {
    log.info("service-getOrganization");

    List<MemberDto> memberList = approvalMapper.getOrganization();

    Map<String, List<MemberDto>> groupedByDepartment = memberList.stream()
            .collect(Collectors.groupingBy(MemberDto::getDepartmentName));

    return groupedByDepartment.entrySet().stream()
            .map(entry -> new DepartmentWithEmployeesDto(entry.getKey(),
                    entry.getValue()))
            .collect(Collectors.toList());
  }

  // 결재 등록
  @Transactional
  public void addApproval(ApprovalDto approvalDto) {
    log.info("service-addApproval");
    // 1. 결재 신청 등록
    approvalMapper.addApproval(approvalDto);
    // 2. 생성된 approvalId를 사용하여 승인자 등록
    if (approvalDto.getApprovers() != 
    null && !approvalDto.getApprovers().isEmpty()) {
      List<Integer> approverIds = approvalDto.getApprovers().stream()
              .map(approver -> approver.getEmpId().intValue())
              .collect(Collectors.toList());
      approvalMapper.addApprovers(approvalDto.getApprovalId(), approverIds);
    }
  }

  // 결재 상태 변경
  @Transactional
  public void updateStatus(ApprovalDto approvalDto) {
    approvalMapper.updateApproverStatus(approvalDto);
    List<MemberDto> currentApprovers = approvalMapper.readApproval(approvalDto.getApprovalId()).getApprovers();
    for (MemberDto approver : currentApprovers) {
      log.info("승인자 ID: {}, 상태: {}", approver.getEmpId(), approver.getApproverStatusId());
    }
    boolean allApproversApproved = currentApprovers.stream()
            .allMatch(approver -> approver.getApproverStatusId() == 2);
    boolean oneApproverRejected = currentApprovers.stream()
            .anyMatch(approver -> approver.getApproverStatusId() == 3);
    if (allApproversApproved) {
      approvalDto.setStatusId(2);  
      approvalMapper.updateApprovalStatus(approvalDto);
      approvalMapper.insertCalendarFromApproval(approvalDto);
    } else if (oneApproverRejected) {
      approvalDto.setStatusId(3);
      approvalMapper.updateApprovalStatus(approvalDto);
    } else {
      log.info("approval.statusId 변경 없음.");
    }
  }

  // 결재 삭제
  @Transactional
  public int deleteApproval(Integer approvalId) {
    int result = -1;
    log.info("service-deleteApproval");
    approvalMapper.deleteApprovers(approvalId);
    result = approvalMapper.deleteApproval(approvalId);
    return result;
  }
}