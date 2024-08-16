//package com.tdl.hi6.websocket;
//
//import com.tdl.hi6.dto.*;
//import com.tdl.hi6.models.message.ChatMessage;
//import com.tdl.hi6.models.user.User;
//import com.tdl.hi6.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Service;
//
//import java.security.Principal;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentSkipListSet;
//
//@Service
//@RequiredArgsConstructor
//public class OnlineOfflineService {
//    private final Set<UUID> onlineUsers = new ConcurrentSkipListSet<>();
//    private final Map<UUID, Set<String>> userSubscribed = new ConcurrentHashMap<>();
//    private final UserRepository userRepository;
//    private final SimpMessageSendingOperations simpMessageSendingOperations;
//
//    public void addOnlineUser (@AuthenticationPrincipal User user) {
//        if (user == null) { return; }
//        for (UUID userId : onlineUsers) {
//            if (userId.equals(user.getId())) { continue; }
//            simpMessageSendingOperations
//                    .convertAndSend("/topic/online/" + userId,
//                            ChatMessageDTO.builder()
//                                    .type(MessageType.FRIEND_ONLINE)
//                                    .connection(ConnectionDTO.builder()
//                                            .connectionEmail(user.getEmail())
//                                            .build())
//                                    .build());
//        }
//        onlineUsers.add(user.getId());
//    }
//
//    public void removeOnlineUser (@AuthenticationPrincipal User user) {
//        if (user == null) { return; }
//        for (UUID userId : onlineUsers) {
//            if (userId.equals(user.getId())) { continue; }
//            simpMessageSendingOperations
//                    .convertAndSend("/topic/online/" + userId,
//                            ChatMessageDTO.builder()
//                                    .type(MessageType.FRIEND_OFFLINE)
//                                    .connection(ConnectionDTO.builder()
//                                            .connectionEmail(user.getEmail())
//                                            .build())
//                                    .build());
//        }
//        onlineUsers.remove(user.getId());
//    }
//
//    public boolean isUserOnline(UUID userId) {
//        return onlineUsers.contains(userId);
//    }
//
//    public List<UserDTO> getOnlineUsers() {
//        return userRepository.findAllById(onlineUsers).stream()
//                .map(user -> UserDTO.builder()
//                        .names(user.getNames())
//                        .surnames(user.getSurnames())
//                        .imageURL(user.getImageURL())
//                        .email(user.getEmail())
//                        .build()).toList();
//    }
//
//    public void addUserSubscribed(@AuthenticationPrincipal User user, String subscribedChannel) {
//        Set<String> subscriptions = userSubscribed.getOrDefault(user.getId(), new HashSet<>());
//        subscriptions.add(subscribedChannel);
//        userSubscribed.put(user.getId(), subscriptions);
//    }
//
//    public void removeUserSubscribed(@AuthenticationPrincipal User user, String subscribedChannel) {
//        Set<String> subscriptions = userSubscribed.getOrDefault(user.getId(), new HashSet<>());
//        subscriptions.remove(subscribedChannel);
//        userSubscribed.put(user.getId(), subscriptions);
//    }
//
//    public boolean isUserSubscribed(UUID userId, String subscription) {
//        Set<String> subscriptions = userSubscribed.getOrDefault(userId, new HashSet<>());
//        return subscriptions.contains(subscription);
//    }
//
//    public Map<String, Set<String>> getUserSubscribed() {
//        Map<String, Set<String>> result = new HashMap<>();
//        List<User> users = userRepository.findAllById(userSubscribed.keySet());
//        users.forEach(user -> result.put(user.getUsername(), userSubscribed.get(user.getId())));
//        return result;
//    }
//
//    public void notifySender (UUID senderId,
//                              List<ChatMessage> messages,
//                              MessageDeliveryStatus status) {
//        if (!isUserOnline(senderId)) { return; }
//        List<MessageDeliveryUpdate> messageDeliveryUpdates = messages.stream()
//                .map(message -> MessageDeliveryUpdate.builder()
//                        .status(status)
//                        .content(message.getContent())
//                        .build()).toList();
//        for (ChatMessage message : messages) {
//            simpMessageSendingOperations.convertAndSend(
//                    "/topic/" + senderId,
//                    ChatMessageDTO.builder()
//                            .messageDeliveryUpdateList(messageDeliveryUpdates)
//                            .type(MessageType.DELIVERY_UPDATE)
//                            .content(message.getContent())
//                            .build());
//        }
//    }
//}
