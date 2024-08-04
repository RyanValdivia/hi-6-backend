package com.tdl.hi6.models.user;

import com.tdl.hi6.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/public/api/user")
@RequiredArgsConstructor
public class UserControllerPublic {
    private final UserService userService;
    private final JwtService jwtService;

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
        try {
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
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping (value = "/update")
    public ResponseEntity<?> updateUser(@RequestHeader ("Authorization") String token, @RequestBody User user) {
        try {
            String username = jwtService.getUsernameFromToken(token);
            User currentUser = userService.getUserByUsername(username);
            User updatedUser = userService.updateUser(currentUser.getId(), user);
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping (value = "/change-password")
    public ResponseEntity<?> changePassword(@RequestHeader ("Authorization") String token,
                                            @RequestBody PasswordChangeRequest request) {
        try{
            String username = jwtService.getUsernameFromToken(token);
            User currentUser = userService.getUserByUsername(username);
            if(!currentUser.getId().equals(currentUser.getId())) {
                return ResponseEntity.badRequest().body("User not authorized to change password.");
            }
            if(userService.checkPassword(currentUser, request.getOldPassword())) {
                return ResponseEntity.badRequest().body("Old password does not match.");
            }
            userService.changePassword(currentUser, request.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping (value = "/delete")
    public ResponseEntity<Void> deleteUser(@RequestHeader ("Authorization") String token) {
        try {
            String username = jwtService.getUsernameFromToken(token);
            User currentUser = userService.getUserByUsername(username);
            userService.deleteUser(currentUser.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
