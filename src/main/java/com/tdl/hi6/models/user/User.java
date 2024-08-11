package com.tdl.hi6.models.user;

import com.tdl.hi6.models.chatroom.ChatRoom;
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
@EqualsAndHashCode
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

    public void addChatRoom (ChatRoom chatRoom) {
        this.chatRooms.add(chatRoom);
    }
}
