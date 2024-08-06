package com.tdl.hi6.models.user;

import com.tdl.hi6.models.user.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByUsername (String username);

    public Optional<List<User>> findAllByStatus (Status status);

}
