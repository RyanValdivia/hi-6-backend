package com.tdl.hi6.controller;

import com.tdl.hi6.dto.UserDTO;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/public/user")
@RequiredArgsConstructor
public class UserControllerPublic {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAll();
        List<UserDTO> userDTOS = users.stream().map(user -> UserDTO.builder()
                .names(user.getNames())
                .surnames(user.getSurnames())
                .email(user.getEmail())
                .description(user.getDescription())
                .imageURL(user.getImageURL())
                .build()).toList();
        return ResponseEntity.ok(userDTOS);
    }

    @GetMapping ("/public/user/{id}")
    public ResponseEntity<UserDTO> getById (@PathVariable UUID id) {
        User user = userService.getById(id);
        UserDTO res = UserDTO.builder()
                .id(user.getId())
                .names(user.getNames())
                .surnames(user.getSurnames())
                .email(user.getEmail())
                .description(user.getDescription())
                .imageURL(user.getImageURL())
                .build();
        return ResponseEntity.ok(res);
    }
}
