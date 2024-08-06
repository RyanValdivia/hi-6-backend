package com.tdl.hi6.models.chatroom;

import com.tdl.hi6.jwt.JwtService;
import com.tdl.hi6.models.groupmessage.ChatNotification;
import com.tdl.hi6.models.groupmessage.GroupMessage;
import com.tdl.hi6.models.groupmessage.GroupMessageDTO;
import com.tdl.hi6.models.groupmessage.GroupMessageService;
import com.tdl.hi6.models.groupmessage.enums.Cause;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.models.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GroupMessageService groupMessageService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final JwtService jwtService;

    @MessageMapping ("/group-message")
    public void sendGroupMessage
            (@Payload GroupMessageDTO message, @AuthenticationPrincipal User user) {
        groupMessageService.sendMessage
                (message.getChatRoomId(), user.getId(), message.getContent());
        GroupMessageDTO groupMessageDTO = GroupMessageDTO.builder()
                .chatRoomId(message.getChatRoomId())
                .senderId(user.getId())
                .content(message.getContent())
                .timestamp(new Date())
                .build();
        simpMessagingTemplate.convertAndSend("/broadcast/" + message.getChatRoomId(), groupMessageDTO);
    }

    @MessageMapping ("/create-chatroom")
    public void createChatRoom (@Payload ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(chatRoomDTO.getName());
        ChatNotification notification = ChatNotification.builder()
                .content("Chat room" + chatRoomDTO.getName() + " created")
                .chatRoomId(chatRoom.getId())
                .cause(Cause.CREATED_CHATROOM)
                .build();
        simpMessagingTemplate.convertAndSend("/broadcast/public",  notification);
    }

    @MessageMapping ("/connect-chatroom")
    public void connectToChatRoom
            (@Payload ChatRoomDTO chatRoomDTO, @AuthenticationPrincipal User user) {
        UUID chatRoomId = chatRoomService.getChatRoomByName(chatRoomDTO.getName()).getId();
        chatRoomService.addUserToChatRoom(chatRoomId, user.getId());
        ChatNotification notification = ChatNotification.builder()
                .content("User " + user.getId() + " joined chatroom " + chatRoomId)
                .chatRoomId(chatRoomId)
                .cause(Cause.CONNECT)
                .build();
        simpMessagingTemplate.convertAndSend("/broadcast/" + chatRoomId, notification);
    }

    @MessageMapping ("/disconnect-chatroom")
    public void disconnectToChatRoom
            (@Payload ChatRoomDTO chatRoomDTO, @AuthenticationPrincipal User user) {
        UUID chatRoomId = chatRoomService.getChatRoomByName(chatRoomDTO.getName()).getId();
        chatRoomService.removeUserFromChatRoom(chatRoomId, user.getId());
        ChatNotification notification = ChatNotification.builder()
                .content("User " + user.getId() + " left chatroom " + chatRoomId)
                .chatRoomId(chatRoomId)
                .cause(Cause.DISCONNECT)
                .build();
        simpMessagingTemplate.convertAndSend("/broadcast/" + chatRoomId, notification);
    }

    @GetMapping ("/messages/group/")
    public ResponseEntity<List<GroupMessageDTO>> getGroupMessages
            (@RequestHeader ("Authorization") String token, ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = chatRoomService.getChatRoomByName(chatRoomDTO.getName());
        User user = userService.getUserByUsername(jwtService.getUsernameFromToken(token));
        if (!chatRoom.getUsers().contains(user)) {
            throw new RuntimeException("You aren't in this chat room.");
        }
        List<GroupMessage> list = groupMessageService.getGroupMessagesById(chatRoom.getId());
        List<GroupMessageDTO> dtos = list.stream().map((message) -> GroupMessageDTO.builder()
                        .chatRoomId(message.getChatRoom().getId())
                        .senderId(message.getSender().getId())
                        .timestamp(message.getTimestamp())
                        .content(message.getContent())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
