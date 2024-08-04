package com.tdl.hi6.models.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerAdmin {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<User> getUserById(@RequestParam UUID id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable (value = "id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
