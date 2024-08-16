package com.tdl.hi6.controller;

import com.tdl.hi6.dto.FriendRequestDTO;
import com.tdl.hi6.dto.ReceivedFriendRequest;
import com.tdl.hi6.dto.SentFriendRequest;
import com.tdl.hi6.dto.UserDTO;
import com.tdl.hi6.models.friendrequest.FriendRequest;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.service.FriendRequestService;
import com.tdl.hi6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/private/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendRequestService friendRequestService;
    private final UserService userService;

    @PostMapping ("/send")
    public ResponseEntity<?> sendFriendRequest
            (@AuthenticationPrincipal User sender, @RequestBody FriendRequestDTO friendRequest) {
        friendRequestService.sendFriendRequest(sender.getId(), friendRequest.getReceiverId());
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/accept")
    public ResponseEntity<?> acceptFriendRequest
            (@AuthenticationPrincipal User sender,
             @RequestBody FriendRequestDTO friendRequest) {
        friendRequestService.acceptFriendRequest(sender.getId(), friendRequest.getReceiverId());
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/decline")
    public ResponseEntity<?> declineFriendRequest
            (@AuthenticationPrincipal User sender,
             @RequestBody FriendRequestDTO friendRequest) {
        friendRequestService.declineFriendRequest(sender.getId(), friendRequest.getReceiverId());
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/sent")
    public ResponseEntity<List<?>> getSentFriendRequests
            (@AuthenticationPrincipal User user) {
        User sender = userService.getById(user.getId());
        List<SentFriendRequest> friendRequests = sender.getSentFriendRequests().stream()
                .map(request -> {
                    User receiver = request.getReceiver();
                    UserDTO receiverDTO = UserDTO.builder()
                            .email(receiver.getEmail())
                            .names(receiver.getNames())
                            .surnames(receiver.getSurnames())
                            .description(receiver.getDescription())
                            .imageURL(receiver.getImageURL())
                            .build();
                    return SentFriendRequest.builder()
                            .status(request.getStatus())
                            .receiver(receiverDTO)
                            .build();
                }).toList();
        return ResponseEntity.ok(friendRequests);
    }

    @GetMapping ("/received")
    public ResponseEntity<?> getReceivedFriendRequests (
            @AuthenticationPrincipal User user) {
        List<ReceivedFriendRequest> friendRequests = friendRequestService
                .getReceivedFriendRequests(user.getId())
                .stream().map(request -> {
                    UserDTO sender = UserDTO.builder()
                            .email(request.getSender().getEmail())
                            .names(request.getSender().getNames())
                            .surnames(request.getSender().getSurnames())
                            .description(request.getSender().getDescription())
                            .imageURL(request.getSender().getImageURL())
                            .build();
                    return ReceivedFriendRequest.builder()
                            .sender(sender)
                            .status(request.getStatus())
                            .build();
                })
                .toList();
        return ResponseEntity.ok(friendRequests);
    }
}
