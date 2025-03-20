package com.erp.backend.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessengerVO {
    private Long messengerId;       // 메시지 고유 ID (Primary Key 역할)
    private String content;        // 메시지 내용
    private Long senderId;          // 보낸 사람의 ID (employee 테이블 참조)
    private Long receiverId;        // 받는 사람의 ID (1:1 채팅일 경우)
    private Long teamChatId;        // 팀 채팅방 ID (팀 채팅일 경우)
    private LocalDateTime sendTime; // 메시지 보낸 시간
    private int readStatus;        // 메시지 읽음 여부 (0: 안 읽음, 1: 읽음)
    private boolean fileAttached;  // 파일 첨부 여부 (true: 있음, false: 없음)
    private String filePath;       // 첨부된 파일 경로 (파일이 있을 경우 저장)
    private int messengerType;     // 메시지 유형 (1: 일반 메시지, 2: 공지사항 등)
    private int messengerStatus;   // 메시지 상태 (1: 정상, 2: 삭제됨 등)

}

