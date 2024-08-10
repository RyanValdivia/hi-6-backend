package com.tdl.hi6.controller;

import com.tdl.hi6.dto.ChatRoomDTO;
import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.UserRepository;
import com.tdl.hi6.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping ("/public/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createChatRoom (@RequestBody ChatRoomDTO chatRoomDTO) {
        chatRoomService.create(chatRoomDTO.getTitle());
        return ResponseEntity.ok(chatRoomDTO);
    }

    @PostMapping ("/add-user")
    public ResponseEntity<?> addUser
            (@AuthenticationPrincipal User user, @RequestBody ChatRoomDTO chatRoomDTO) {
        chatRoomService.addUser(chatRoomDTO.getTitle(), user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/delete-user")
    public ResponseEntity<?> deleteUser
            (@AuthenticationPrincipal User user, @RequestBody ChatRoomDTO chatRoomDTO) {
        chatRoomService.removeUser(chatRoomDTO.getTitle(), user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAll () {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }

    @PostMapping ("/test1")
    public ResponseEntity<?> test (@AuthenticationPrincipal User user,
                                   @RequestBody ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomDTO.getTitle());
        return ResponseEntity.ok(new ArrayList<>(chatRoom.getUsers()));
    }

    @GetMapping ("/test2")
    public ResponseEntity<?> test2 (@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatRoomService.getChatRooms(user.getId()));
    }

    @GetMapping ("/test3")
    public ResponseEntity<?> getAllTest() {
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        chatRooms.forEach(chatRoom -> {
            System.out.println("ChatRoom: " + chatRoom.getTitle());
            chatRoom.getUsers().forEach(user -> System.out.println("User: " + user.getEmail()));
        });
        return ResponseEntity.ok(chatRooms);
    }
}
