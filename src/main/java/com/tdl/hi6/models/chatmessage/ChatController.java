package com.tdl.hi6.models.chatmessage;

import com.tdl.hi6.models.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendPrivateMessage (@AuthenticationPrincipal User sender) {

    }
}
