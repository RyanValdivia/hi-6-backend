package com.tdl.hi6.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable (value = "id") UUID id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable (value = "id") UUID id, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(id, user));
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable (value = "id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
