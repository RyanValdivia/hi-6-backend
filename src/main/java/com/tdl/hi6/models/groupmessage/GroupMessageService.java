package com.tdl.hi6.models.groupmessage;

import com.tdl.hi6.models.chatmessage.ChatMessage;
import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.chatroom.ChatRoomRepository;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.models.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupMessageService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final GroupMessageRepository groupMessageRepository;

    public GroupMessage sendMessage(UUID chatRoomId, UUID senderId, String content) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!chatRoom.getUsers().contains(sender)) {
            throw new RuntimeException("User is not in chat room");
        }
        GroupMessage groupMessage = GroupMessage.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(content)
                .build();
        return groupMessageRepository.save(groupMessage);
    }

    public List<GroupMessage> getGroupMessagesById(UUID chatRoomId) {
        return groupMessageRepository.findByChatRoomId(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
    }
}
