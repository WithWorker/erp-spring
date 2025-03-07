package com.erp.backend.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDto {
    private Integer approvalId;

    @JsonProperty("type_id") 
    private Integer typeId;

    @JsonProperty("start_date") // JSON 키와 매핑
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonProperty("end_date") 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String title;

    private String content;

    @JsonProperty("applicant_id")
    private Integer applicantId;
    
    @JsonProperty("status_id")
    private Integer statusId;

    private String typeName; 
    private String statusName;  

    private MemberDto applicant;

    private CalendarDto calendarDto;

    private List<MemberDto> approvers;
}

