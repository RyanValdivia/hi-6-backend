package com.tdl.hi6.models.chatroom;

import com.tdl.hi6.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (unique = true, nullable = false)
    private String title;

    @ManyToMany (mappedBy = "chatRooms")
    private Set<User> users;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(id, chatRoom.id) && title.equals(chatRoom.title);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, title);
    }
}
