package com.tdl.hi6.models.chatroom;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tdl.hi6.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ChatRoom {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (unique = true, nullable = false)
    private String title;
}
