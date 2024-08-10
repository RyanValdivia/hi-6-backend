package com.tdl.hi6.repository;

import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail (String email);
//    public Optional<List<ChatRoom>> findChatRoomsByUserId (UUID userId);
}
