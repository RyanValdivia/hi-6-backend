package com.tdl.hi6.service;

import com.tdl.hi6.dto.ChatMessageDTO;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository messageRepository;

    public void sendMessage (ChatMessageDTO messageDTO, User sender) {

    }
}
