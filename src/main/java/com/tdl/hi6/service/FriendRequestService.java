package com.tdl.hi6.service;

import com.tdl.hi6.models.friendrequest.FriendRequest;
import com.tdl.hi6.models.friendrequest.Status;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    public void sendFriendRequest(UUID senderId, UUID receiverId) {
        User sender = userService.getById(senderId);
        User receiver = userService.getById(receiverId);
        FriendRequest friendRequest = friendRequestRepository
                .findBySenderAndReceiver(sender, receiver)
                .orElseGet(() -> FriendRequest.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .build());
        friendRequest.setStatus(Status.PENDING);
        friendRequestRepository.save(friendRequest);
    }

    public void acceptFriendRequest(UUID senderId, UUID receiverId) {
        User sender = userService.getById(senderId);
        User receiver = userService.getById(receiverId);
        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));
        if (friendRequest.getStatus() == Status.ACCEPTED) {
            throw new RuntimeException("Friend request is already accepted");
        }
        friendRequest.setStatus(Status.ACCEPTED);
        friendRequestRepository.save(friendRequest);
        sender.addFriend(receiver);
        userService.save(sender);
    }

    public void declineFriendRequest(UUID senderId, UUID receiverId) {
        User sender = userService.getById(senderId);
        User receiver = userService.getById(receiverId);
        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));
        if (friendRequest.getStatus() == Status.DECLINED) {
            throw new RuntimeException("Friend request is already declined");
        }
        friendRequest.setStatus(Status.DECLINED);
        friendRequestRepository.save(friendRequest);
    }

    public Set<FriendRequest> getSentFriendRequests (UUID userId) {
        User user = userService.getById(userId);
        return user.getSentFriendRequests();
    }

    public Set<FriendRequest> getReceivedFriendRequests (UUID userId) {
        User user = userService.getById(userId);
        return user.getReceivedFriendRequests();
    }

}
