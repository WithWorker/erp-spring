package com.erp.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BonusHistoryDto {
    private Long bonusId;
    private Long empId;
    private LocalDate paymentDate;  // 지급 날짜 (YYYY-MM-DD)
    private Integer amount;
}
