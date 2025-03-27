package com.erp.backend.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupMessageDTO {
    private Long messengerId;         // 메시지 Id
    private Long senderId;            // 보내는 사람 ID
    private List<Long> receiverIds;   // 여러 명의 수신자 ID
    private String content;           // 메시지 내용
    private String filePath;          // 파일 경로(필요 시)
    private Integer roomId;           // 단체 채팅방 Id
}
