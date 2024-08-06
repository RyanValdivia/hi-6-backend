package com.tdl.hi6.models.user;

import com.tdl.hi6.models.user.enums.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional (rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll () {
        return userRepository.findAll();
    }

    public User getUser (UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser (UUID id) {
        userRepository.deleteById(id);
    }

    public User updateUser (UUID id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setNames(userDetails.getNames());
        user.setSurnames(userDetails.getSurnames());
        user.setDescription(userDetails.getDescription());
        return userRepository.save(user);
    }

    public User getUserByUsername (String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean checkPassword (User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public void changePassword (User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void connectUser (UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }

    public void disconnectUser (UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(Status.OFFLINE);
        userRepository.save(user);
    }

    public List<User> getConnectedUsers () {
        return userRepository.findAllByStatus(Status.ONLINE)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}

