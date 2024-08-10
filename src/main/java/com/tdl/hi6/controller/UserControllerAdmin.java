package com.tdl.hi6.controller;

import com.tdl.hi6.dto.UserDTO;
import com.tdl.hi6.models.user.User;
import com.tdl.hi6.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/admin/user")
@RequiredArgsConstructor
public class UserControllerAdmin {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<User> getUser (@AuthenticationPrincipal User user) {
        User current = userService.getById(user.getId());
        return ResponseEntity.ok(current);
    }
}
