package com.tdl.hi6.models.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    public Optional<ChatRoom> findByName(String name);
}
