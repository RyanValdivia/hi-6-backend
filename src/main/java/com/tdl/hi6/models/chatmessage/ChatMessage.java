package com.tdl.hi6.models.chatmessage;

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
public class ChatMessage {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;
    @Column (nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn (name = "sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @JoinColumn (name = "recipient_id", nullable = false)
    private User recipient;
    private Date timestamp;
}
