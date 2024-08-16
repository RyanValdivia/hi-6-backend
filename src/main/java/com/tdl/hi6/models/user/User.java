package com.tdl.hi6.models.user;

import com.tdl.hi6.dto.UserDTO;
import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.friendrequest.FriendRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false, unique = true)
    private String email;
    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String names;
    @Column (nullable = false)
    private String surnames;
    private String description;

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    private Role role;
    @Column (name = "image_url")
    private String imageURL;

    private boolean online;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "users_chat_rooms_map",
    joinColumns = @JoinColumn (name = "user_email", referencedColumnName = "email"),
    inverseJoinColumns = @JoinColumn (name = "chat_room_title", referencedColumnName = "title"))
    private Set<ChatRoom> chatRooms;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(name = "friends_map",
            joinColumns = @JoinColumn(name = "user_email", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(name = "friend_email", referencedColumnName = "email"))
    private Set<User> friends;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "sender",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FriendRequest> sentFriendRequests;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "receiver",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FriendRequest> receivedFriendRequests;

    @Override
    public boolean isEnabled () {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }

    @Override
    public boolean isAccountNonLocked () {
        return true;
    }

    @Override
    public boolean isAccountNonExpired () {
        return true;
    }

    @Override
    public String getUsername () {
        return this.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, email);
    }

    public boolean addChatRoom (ChatRoom chatRoom) {
        return this.chatRooms.add(chatRoom);
    }

    public boolean removeChatRoom (ChatRoom chatRoom) {
        return this.chatRooms.remove(chatRoom);
    }

    public boolean addFriendRequest (FriendRequest friendRequest) {
        return this.sentFriendRequests.add(friendRequest);
    }

    public boolean addFriend (User user) {
        return this.friends.add(user);
    }

    public boolean removeFriend (User user) {
        return this.friends.remove(user);
    }
}
