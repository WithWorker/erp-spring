package com.erp.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.erp.backend.dto.ApprovalDto;

@Mapper
public interface ApprovalMapper {
    List<ApprovalDto> getApplicant(Integer applicantId);
    List<ApprovalDto> getApprover(Integer approverId);
    List<ApprovalDto> getApprovedList();
    ApprovalDto readApproval(Integer approvalId);
    int addApproval(ApprovalDto approvalDto);
    int updateApproval(ApprovalDto approvalDto);
    int deleteApproval(Integer approvalId);
} 