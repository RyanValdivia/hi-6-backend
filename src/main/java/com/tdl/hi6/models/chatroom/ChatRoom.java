package com.tdl.hi6.models.chatroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToMany (fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "chatRooms")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

//    @OneToMany
//    private List<GroupMessage> groupMessages;
}
