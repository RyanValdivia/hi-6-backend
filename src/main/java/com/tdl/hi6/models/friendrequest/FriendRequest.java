package com.tdl.hi6.models.friendrequest;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendRequest {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn (nullable = false, name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn (nullable = false, name = "receiver_id")
    private User receiver;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private Status status;

    private Date timestamp;
}
