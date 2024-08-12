package com.tdl.hi6.service;

import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.ChatRoomRepository;
import com.tdl.hi6.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional (rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ChatRoom create (String title) {
        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    public void addUser (UUID userId, String name) {
        userService.addChatRoomToUser(userId, name);
    }

    public void removeUser (UUID userId, String name) {
        userService.removeChatRoomFromUser(userId, name);
    }

    public ChatRoom getChatRoom (String title) {
        return chatRoomRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("No chat room found for title: " + title));
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }
}
