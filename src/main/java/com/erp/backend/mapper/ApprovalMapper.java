package com.erp.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.erp.backend.dto.ApprovalDto;
import com.erp.backend.dto.CalendarDto;

@Mapper
public interface ApprovalMapper {
    List<ApprovalDto> getApplicant(Integer applicantId);
    List<ApprovalDto> getApprover(Integer approverId);
    List<ApprovalDto> getApprovedList();
    ApprovalDto readApproval(Integer approvalId);
    int addApproval(ApprovalDto approvalDto);
    int updateApproval(ApprovalDto approvalDto);
    CalendarDto getCalendarByApprovalId(Integer approvalId); // 별도로 Calendar 조회
    int deleteApproval(Integer approvalId);
} 