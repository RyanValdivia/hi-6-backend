package com.tdl.hi6.controller;

import com.tdl.hi6.dto.ChatRoomDTO;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.service.ChatRoomService;
import com.tdl.hi6.service.UserService;
import jakarta.transaction.Transactional;
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
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }

    @GetMapping ("/all-from-user")
    public ResponseEntity<?> getAllFromUser (@AuthenticationPrincipal User user) {
        User current = userService.getById(user.getId());
        return ResponseEntity.ok(current.getChatRooms());
    }

}
