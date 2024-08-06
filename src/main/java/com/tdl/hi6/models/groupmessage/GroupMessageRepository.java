package com.tdl.hi6.models.groupmessage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, UUID> {
    public Optional<List<GroupMessage>> findByChatRoomId(UUID chatRoomId);
}
