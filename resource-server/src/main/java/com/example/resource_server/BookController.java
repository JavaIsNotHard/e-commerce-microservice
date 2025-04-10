package com.example.resource_server;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    @GetMapping("/books")
    public ResponseEntity<String> getBooks(Authentication authentication) {
        assert authentication instanceof JwtAuthenticationToken;
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String username = authentication.getName();
        String jwtString = jwtAuthenticationToken.getToken().getTokenValue();

        return ResponseEntity.ok("Hi" + username + ", here are some books" + " here is you code " + jwtString);
    }
}
