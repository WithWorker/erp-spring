package com.erp.backend.controller;

import com.erp.backend.model.MessengerVO;
import com.erp.backend.service.MessengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate smt;

    @Autowired
    private MessengerService ms;

    /**
     * - 1:1 채팅 처리
     * - /app/chat.one 로 요청 시
     * - /user/{receiverId}/one/messages 로 전송
     */
    @MessageMapping("/chat.one")
    public void sendPrivateMessage(MessengerVO mvo) {
        ms.sendMessage(mvo);
        smt.convertAndSendToUser(String.valueOf(mvo.getSenderId()), "/one/messages", mvo);
    }

    /**
     * - 1:N 그룹 채팅 처리
     * - /app/chat.all 로 요청 시
     * - /all/chatroom/{teamChatId} 로 브로드캐스트
     */
    @MessageMapping("/chat.all")
    public void sendAllMessage(MessengerVO mvo) {
        ms.sendMessage(mvo);
        smt.convertAndSend("/all/chatroom/" + mvo.getSenderId(), mvo);
    }
}
