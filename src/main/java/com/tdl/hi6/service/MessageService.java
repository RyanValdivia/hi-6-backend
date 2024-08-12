package com.tdl.hi6.service;

import com.tdl.hi6.dto.MessageDTO;
import com.tdl.hi6.models.message.Message;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;

    public void sendMessage (MessageDTO messageDTO, User sender) {

    }
}
