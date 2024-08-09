package com.tdl.hi6.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
                .build()).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOS);
    }
}
