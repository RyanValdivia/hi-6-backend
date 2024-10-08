package com.tdl.hi6.controller;

import com.tdl.hi6.dto.ChatRoomDTO;
import com.tdl.hi6.dto.UserDTO;
import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.service.ChatRoomService;
import com.tdl.hi6.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping ("/public/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createChatRoom (@RequestBody ChatRoomDTO chatRoomDTO) {
        chatRoomService.create(chatRoomDTO.getTitle());
        return ResponseEntity.ok(chatRoomDTO);
    }

    @PostMapping ("/add-user")
    public ResponseEntity<?> addUser
            (@AuthenticationPrincipal User user, @RequestBody ChatRoomDTO chatRoomDTO) {
        chatRoomService.addUser(user.getId(), chatRoomDTO.getTitle());
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/remove-user")
    public ResponseEntity<?> removeUser
            (@AuthenticationPrincipal User user, @RequestBody ChatRoomDTO chatRoomDTO) {
        chatRoomService.removeUser(user.getId(), chatRoomDTO.getTitle());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAll () {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms().stream()
                .map(chatRoom -> ChatRoomDTO.builder()
                        .title(chatRoom.getTitle())
                        .build()).toList());
    }

    @Transactional
    @GetMapping ("/all-from-user")
    public ResponseEntity<?> getAllFromUser (@AuthenticationPrincipal User user) {
        User current = userService.getById(user.getId());
        List<ChatRoomDTO> rooms = current.getChatRooms().stream().map((chatRoom ->
                ChatRoomDTO.builder()
                        .title(chatRoom.getTitle())
                        .build())).toList();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping ("all-from-chatroom")
    public ResponseEntity<?> allFromChatRoom
            (@AuthenticationPrincipal User userDetails, @RequestBody ChatRoomDTO chatRoomDTO) {
        ChatRoom current = chatRoomService.getChatRoom(chatRoomDTO.getTitle());
        if (!current.getUsers().contains(userDetails)) {
            return ResponseEntity.badRequest().body("The user is not in that ChatRoom");
        }
        return ResponseEntity.ok(current.getUsers().stream()
                .map(user -> UserDTO.builder()
                        .email(user.getEmail())
                        .names(user.getNames())
                        .surnames(user.getSurnames())
                        .description(user.getDescription())
                        .imageURL(user.getImageURL())
                        .build()).toList());
    }

}
