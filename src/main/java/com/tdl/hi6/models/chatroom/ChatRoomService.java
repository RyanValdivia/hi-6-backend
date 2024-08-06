package com.tdl.hi6.models.chatroom;

import com.tdl.hi6.models.user.User;
import com.tdl.hi6.models.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoom createChatRoom(String name) {
        if (chatRoomRepository.findByName(name) == null) {
            throw new RuntimeException("Chat room already exists");
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .users(new HashSet<>())
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom addUserToChatRoom(UUID chatRoomId, UUID userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        chatRoom.getUsers().add(user);
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom removeUserFromChatRoom(UUID chatRoomId, UUID userId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        chatRoom.getUsers().remove(user);
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom getChatRoomByName(String name) {
        return chatRoomRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));
    }
}
