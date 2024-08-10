package com.tdl.hi6.controller;

import com.tdl.hi6.dto.ChatRoomDTO;
import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.UserRepository;
import com.tdl.hi6.service.ChatRoomService;
import com.tdl.hi6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


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
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/delete-user")
    public ResponseEntity<?> deleteUser
            (@AuthenticationPrincipal User user, @RequestBody ChatRoomDTO chatRoomDTO) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAll () {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }

}
