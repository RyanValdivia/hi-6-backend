package com.tdl.hi6.controller;

import com.tdl.hi6.dto.ChatRoomDTO;
import com.tdl.hi6.dto.UserDTO;
import com.tdl.hi6.models.user.ChangePasswordRequest;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping ("/private/user")
@RequiredArgsConstructor
public class UserControllerPrivate {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getUser (@AuthenticationPrincipal User user) {
        User current = userService.getById(user.getId());
        UserDTO userDTO = UserDTO.builder()
                .names(current.getNames())
                .surnames(current.getSurnames())
                .email(current.getEmail())
                .imageURL(current.getImageURL())
                .description(current.getDescription())
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

    @PutMapping ("/change-password")
    public ResponseEntity<Void> changePassword
            (@AuthenticationPrincipal User user, @RequestBody ChangePasswordRequest request) {
        userService.changePassword(user.getId(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }

}
