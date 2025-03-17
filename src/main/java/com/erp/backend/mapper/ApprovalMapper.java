package com.erp.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.erp.backend.dto.ApprovalDto;

@Mapper
public interface ApprovalMapper {
    
    //결재 목록 조회 (by 신청자)
    List<ApprovalDto> getApplicant(Integer applicantId);
    
    //결재 대기 목록 조회 (by 신청자)
    List<ApprovalDto> getApplicantPending(Integer applicantId);
    
    //결재 승인 목록 조회 (by 신청자)
    List<ApprovalDto> getApplicantApproved(Integer applicantId);
    
    //결재 목록 조회 (by 승인자)
    List<ApprovalDto> getApprover(Integer approverId);

    //결재 목록 조회 (by 상태)
    List<ApprovalDto> getApprovedList(Integer statusId);

    //결재 상세보기 - 승인자 각각 상태
    ApprovalDto readApproval(Integer approvalId);
    
    //결재 등록 - 신청자
    void addApproval(ApprovalDto approvalDto);
    void addApprovers(@Param("approvalId") Integer approvalId, @Param("approvers") List<Integer> approvers);//승인자 추가

    //결재 상태 수정 - 승인자
    int updateApproverStatus(ApprovalDto approvalDto);//승인자 각각 상태 변경
    void updateApprovalStatus(ApprovalDto approvalDto);//최종 상태 변경
    int insertCalendarFromApproval(ApprovalDto approvalDto);//최종 상태 2로 변경되면 캘린더에 추가

    //결재 삭제
    int deleteApprovers(Integer approvalId);//승인자 삭제
    int deleteApproval(Integer approvalId);//결재 삭제
} 