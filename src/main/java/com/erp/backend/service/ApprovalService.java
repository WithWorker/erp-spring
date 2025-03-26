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
    log.info("승인자 ID: {}, 변경할 상태: {}", approvalDto.getApproverId(), approvalDto.getApproverStatusId());

    // 승인자 상태 업데이트
    approvalMapper.updateApproverStatus(approvalDto);

    // 최신 승인자 상태 목록 조회
    List<MemberDto> currentApprovers = approvalMapper.readApproval(approvalDto.getApprovalId()).getApprovers();
    log.info("현재 승인자 상태 목록 (DB 최신 데이터):");
    for (MemberDto approver : currentApprovers) {
      log.info("승인자 ID: {}, 상태: {}", approver.getEmpId(), approver.getApproverStatusId());
    }

    // 모든 승인자가 승인(2)인지 확인
    boolean allApproversApproved = currentApprovers.stream()
            .allMatch(approver -> approver.getApproverStatusId() == 2);
    // 한명의 승인자가 반려(3)인지 확인
    boolean oneApproverRejected = currentApprovers.stream()
            .anyMatch(approver -> approver.getApproverStatusId() == 3);

    if (allApproversApproved) {
      log.info("모든 승인자가 승인 상태(2)입니다. approval.statusId를 2로 변경합니다.");
      approvalDto.setStatusId(2);  // 상태를 승인 상태로 설정
      approvalMapper.updateApprovalStatus(approvalDto);
      log.info("approval.statusId가 2로 업데이트됨 (approvalId: {})", approvalDto.getApprovalId());
      // 상태가 2로 변경되었으므로 캘린더에 자동으로 등록
      approvalMapper.insertCalendarFromApproval(approvalDto);
    } else if (oneApproverRejected) {
      log.info("한명의 승인자가 반려 상태(3)입니다. approval.statusId를 3으로 변경합니다.");
      approvalDto.setStatusId(3);
      approvalMapper.updateApprovalStatus(approvalDto);
    } else {
      log.info("아직 모든 승인자가 승인, 한명이라도 반려 상태가 아님. approval.statusId 변경 없음.");
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