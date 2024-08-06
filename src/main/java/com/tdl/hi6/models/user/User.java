package com.tdl.hi6.models.user;

import com.tdl.hi6.models.user.enums.Role;
import com.tdl.hi6.models.user.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table (name = "users",
        uniqueConstraints = {@UniqueConstraint (columnNames = {"username", "email"})})
public class User implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false)
    private String username;
    @Column (nullable = false)
    private String password;
    @Column (nullable = false)
    private String names;
    @Column (nullable = false)
    private String surnames;
    @Column (nullable = false)
    private String email;
    private String description;
    @Enumerated (EnumType.STRING)
    private Role role;
    @Enumerated (EnumType.STRING)
    private Status status;
    @Column (name = "image_url")
    private String imageURL;

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
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

}
