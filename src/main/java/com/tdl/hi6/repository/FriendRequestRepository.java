package com.tdl.hi6.repository;

import com.tdl.hi6.models.friendrequest.FriendRequest;
import com.tdl.hi6.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, UUID> {
    public Optional<FriendRequest> findBySenderAndReceiver (User sender, User receiver);
}
