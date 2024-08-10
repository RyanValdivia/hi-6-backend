package com.tdl.hi6.service;

import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.ChatRoomRepository;
import com.tdl.hi6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ChatRoom create (String title) {
        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    public void addUser (String title, UUID userId) {
        User user = userService.getById(userId);
        ChatRoom chatRoom = chatRoomRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("No chat room found for title: " + title));
        chatRoom.getUsers().add(user);
        user.getChatRooms().add(chatRoom);
        chatRoomRepository.save(chatRoom);
        userRepository.save(user);
    }

    public void removeUser (String title, UUID userId) {
        User user = userService.getById(userId);
        ChatRoom chatRoom = chatRoomRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("No chat room found for title: " + title));
        chatRoom.getUsers().remove(user);
        user.getChatRooms().remove(chatRoom);
        chatRoomRepository.save(chatRoom);
        userRepository.save(user);
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }
}
