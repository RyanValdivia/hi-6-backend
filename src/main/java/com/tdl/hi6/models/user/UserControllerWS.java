package com.tdl.hi6.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class UserControllerWS {
    private final UserService userService;

    @MessageMapping ("/user.addUser")
    @SendTo ("/user/public")
    public User addUser (@AuthenticationPrincipal User user) {

    }

}
