package com.erp.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.erp.backend.dto.ApprovalDto;

@Mapper
public interface ApprovalMapper {
    List<ApprovalDto> getApplicant(Integer applicantId);
    List<ApprovalDto> getApprover(Integer approverId);
    List<ApprovalDto> getApprovedList(Integer statusId);
    ApprovalDto readApproval(Integer approvalId);
    void addApproval(ApprovalDto approvalDto);
    void addApprovers(@Param("approvalId") Integer approvalId, @Param("approvers") List<Integer> approvers);
    int updateStatus(ApprovalDto approvalDto);
    int insertCalendarFromApproval(ApprovalDto approvalDto);
    int deleteApprovers(Integer approvalId);
    int deleteApproval(Integer approvalId);
} 