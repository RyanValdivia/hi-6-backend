package com.tdl.hi6.repository;

import com.tdl.hi6.models.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
