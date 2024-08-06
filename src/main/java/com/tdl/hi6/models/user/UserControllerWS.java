package com.tdl.hi6.models.user;

import com.tdl.hi6.models.user.enums.Status;
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

    @MessageMapping ("/user.connect")
    @SendTo ("/user/public")
    public UserDTO connectUser (@AuthenticationPrincipal User user) {
        userService.connectUser(user.getId());
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .names(user.getNames())
                .surnames(user.getSurnames())
                .description(user.getDescription())
                .email(user.getEmail())
                .role(user.getRole())
                .imageURL(user.getImageURL())
                .build();
        return userDTO;
    }

    @MessageMapping ("/user.disconnect")
    @SendTo ("/user/public")
    public UserDTO disconnectUser (@AuthenticationPrincipal User user) {
        userService.disconnectUser(user.getId());
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .names(user.getNames())
                .surnames(user.getSurnames())
                .description(user.getDescription())
                .email(user.getEmail())
                .role(user.getRole())
                .imageURL(user.getImageURL())
                .build();
        return userDTO;
    }



}
