package com.tdl.hi6.service;

import com.tdl.hi6.dto.UserDTO;
import com.tdl.hi6.models.chatroom.ChatRoom;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.repository.ChatRoomRepository;
import com.tdl.hi6.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Transactional (rollbackOn = Exception.class)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void save (User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public User updateUser (UUID id, UserDTO user) {
        User current = getById(id);
        current.setEmail(user.getEmail());
        current.setNames(user.getNames());
        current.setSurnames(user.getSurnames());
        current.setDescription(user.getDescription());
        current.setImageURL(user.getImageURL());
        return userRepository.save(current);
    }

    public void changePassword(UUID id, String oldPassword, String newPassword) {
        User current = getById(id);
        if (!current.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Wrong password");
        }
        current.setPassword(newPassword);
        userRepository.save(current);
    }

    public void addChatRoomToUser (UUID userId, String chatRoomTitle) {
        ChatRoom chatRoom = chatRoomRepository.findByTitle(chatRoomTitle)
                .orElseThrow(() -> new RuntimeException("Chat room with title " + chatRoomTitle + " not found"));
        User user = getById(userId);
        boolean res = user.addChatRoom(chatRoom);
        if (!res) {
            throw new RuntimeException("User with id " + userId + " already has a chat room");
        }
        userRepository.save(user);
    }

    public void removeChatRoomFromUser (UUID userId, String chatRoomTitle) {
        ChatRoom chatRoom = chatRoomRepository.findByTitle(chatRoomTitle)
                .orElseThrow(() -> new RuntimeException("Chat room with title " + chatRoomTitle + " not found"));
        User user = getById(userId);
        boolean res = user.removeChatRoom(chatRoom);
        if (!res) {
            throw new RuntimeException("User with id " + userId + " already has a chat room");
        }
        userRepository.save(user);
    }

    public void addFriend (UUID userId, UUID friendId) {
        User current = getById(userId);
        User friend = getById(friendId);
        if (current.getFriends().contains(friend)) {
            throw new RuntimeException("Friend " + friendId + " already exists");
        }
        current.addFriend(friend);
        userRepository.save(current);
    }

    public void removeFriend (UUID userId, UUID friendId) {
        User current = getById(userId);
        User friend = getById(friendId);
        if (!current.getFriends().contains(friend)) {
            throw new RuntimeException("Friend " + friendId + " does not exist");
        }
        current.removeFriend(friend);
        userRepository.save(current);
    }

    public Set<User> getFriends (UUID userId) {
        User current = getById(userId);
        return current.getFriends();
    }
}
