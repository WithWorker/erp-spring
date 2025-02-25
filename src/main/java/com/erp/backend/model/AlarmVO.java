package com.erp.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class AlarmVO {

    private String alarmId; // 알람 ID
    private String alarmName; // 알람 이름
    private String alarmDesc; // 알람 설명
    private String alarmTime; // 알람 발생 시간
    private String alarmStatus; // 알람 상태
    private String alarmType; // 알람 유형
    private String alarmLevel; // 알람 중요도
    private String alarmSource; // 알람 발생 원인
    private String receiverId;    // 알람을 받는 사용자 ID (employee 테이블 참조)
    private boolean isRead;    // 알람 읽음 여부 (true: 읽음, false: 안 읽음)
    private LocalDateTime createdAt; // 알람 생성 시간
    private String url; // 알람 링크 (메인)
    private String url2; // 알람 링크 (추가링크 정보)

}
