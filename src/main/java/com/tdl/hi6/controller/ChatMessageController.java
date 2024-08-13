package com.tdl.hi6.controller;

import com.tdl.hi6.dto.ChatMessageDTO;
import com.tdl.hi6.models.message.ChatMessage;
import com.tdl.hi6.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @MessageMapping ("/chat/send-message/{convId}")
    public ChatMessage sendMessage
            (@DestinationVariable("convId") String conversationId,
             @Payload ChatMessageDTO chatMessageDTO) {

    }
}
