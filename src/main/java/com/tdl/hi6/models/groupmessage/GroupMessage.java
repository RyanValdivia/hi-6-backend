package com.tdl.hi6.models.groupmessage;

import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessage {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;
    @Column (nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn (name = "sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @JoinColumn (name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;
    private Date timestamp;
}
