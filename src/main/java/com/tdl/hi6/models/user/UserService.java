package com.tdl.hi6.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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

}
