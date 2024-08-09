package com.tdl.hi6.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/private/user")
@RequiredArgsConstructor
public class UserControllerPrivate {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser (@AuthenticationPrincipal User user) {
        UserDTO userDTO = UserDTO.builder()
                .names(user.getNames())
                .surnames(user.getSurnames())
                .email(user.getEmail())
                .imageURL(user.getImageURL())
                .description(user.getDescription())
                .build();
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser (@AuthenticationPrincipal User user) {
        userService.deleteById(user.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser (@AuthenticationPrincipal User user, @RequestBody UserDTO userDetails) {
        User res = userService.updateUser(user.getId(), userDetails);
        UserDTO userDto = UserDTO.builder()
                .names(res.getNames())
                .surnames(res.getSurnames())
                .email(res.getEmail())
                .imageURL(res.getImageURL())
                .description(res.getDescription())
                .build();
        return ResponseEntity.ok(userDto);
    }

}
