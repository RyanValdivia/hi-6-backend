package com.tdl.hi6.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserDTO> userDTOs = users.stream().map(user -> UserDTO.builder()
                        .id(user.getId())
                        .names(user.getNames())
                        .surnames(user.getSurnames())
                        .description(user.getDescription())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .imageURL(user.getImageURL())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(userDTOs);
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable (value = "id") UUID id) {
        User user = userService.getUser(id);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .names(user.getNames())
                .surnames(user.getSurnames())
                .description(user.getDescription())
                .email(user.getEmail())
                .role(user.getRole())
                .imageURL(user.getImageURL())
                .build();
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable (value = "id") UUID id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        UserDTO userDTO = UserDTO.builder()
                .id(updatedUser.getId())
                .names(updatedUser.getNames())
                .surnames(updatedUser.getSurnames())
                .description(updatedUser.getDescription())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole())
                .imageURL(updatedUser.getImageURL())
                .build();
        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable (value = "id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
