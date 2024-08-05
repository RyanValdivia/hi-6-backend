package com.tdl.hi6.models.user;

import com.tdl.hi6.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/public/api/user")
@RequiredArgsConstructor
public class UserControllerPublic {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers () {
        List<User> users = userService.getAll();
        List<UserDTO> userDTOs = users.stream().map(user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
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
    public ResponseEntity<UserDTO> getUserById (@PathVariable (value = "id") UUID id) {

        User user = userService.getUser(id);
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .names(user.getNames())
                .surnames(user.getSurnames())
                .description(user.getDescription())
                .email(user.getEmail())
                .role(user.getRole())
                .imageURL(user.getImageURL())
                .build();
        return ResponseEntity.ok().body(userDTO);

    }

    @PutMapping (value = "/update")
    public ResponseEntity<?> updateUser (@RequestHeader ("Authorization") String token, @RequestBody User user) {

        String username = jwtService.getUsernameFromToken(token);
        User currentUser = userService.getUserByUsername(username);
        User updatedUser = userService.updateUser(currentUser.getId(), user);
        UserDTO userDTO = UserDTO.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .names(updatedUser.getNames())
                .surnames(updatedUser.getSurnames())
                .description(updatedUser.getDescription())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole())
                .imageURL(updatedUser.getImageURL())
                .build();
        return ResponseEntity.ok().body(userDTO);

    }

    @PutMapping (value = "/change-password")
    public ResponseEntity<?> changePassword (@RequestHeader ("Authorization") String token,
                                             @RequestBody PasswordChangeRequest request) {
        String username = jwtService.getUsernameFromToken(token);
        User currentUser = userService.getUserByUsername(username);
        if (userService.checkPassword(currentUser, request.getOldPassword())) {
            return ResponseEntity.badRequest().body("Old password does not match.");
        }
        userService.changePassword(currentUser, request.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping (value = "/delete")
    public ResponseEntity<Void> deleteUser (@RequestHeader ("Authorization") String token) {
        String username = jwtService.getUsernameFromToken(token);
        User currentUser = userService.getUserByUsername(username);
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.ok().build();
    }
}
