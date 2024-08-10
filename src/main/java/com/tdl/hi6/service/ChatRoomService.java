package com.tdl.hi6.service;

import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.ChatRoomRepository;
import com.tdl.hi6.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@Transactional (rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ChatRoom create (String title) {
        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .users(new HashSet<>())
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public void addUser (UUID userId, String name) {
        ChatRoom chatRoom = getChatRoom(name);
        User user = userService.getById(userId);
        boolean res = user.getChatRooms().add(chatRoom);
        if (!res) {
            throw new RuntimeException("User is already in that chat room");
        }
        userRepository.save(user);
    }

    public void removeUser (UUID userId, String name) {
        ChatRoom chatRoom = getChatRoom(name);
        User user = userService.getById(userId);
        boolean res = user.getChatRooms().remove(chatRoom);
        if (!res) {
            throw new RuntimeException("User is not in that chat room");
        }
        userRepository.save(user);
    }

    public ChatRoom getChatRoom (String title) {
        return chatRoomRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("No chat room found for title: " + title));
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }
}
