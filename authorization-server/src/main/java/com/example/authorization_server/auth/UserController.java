package com.example.authorization_server.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/register")
public class UserController {
    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody UserRequestDTO user, Authentication authentication) {
        SecurityUser savedUser = userServices.registerUser(user);
        if (savedUser == null) {
            return ResponseEntity.badRequest().body("SecurityUser already exists");
        }
        return ResponseEntity.ok("created user: " + savedUser.getUsername());
    }

}
