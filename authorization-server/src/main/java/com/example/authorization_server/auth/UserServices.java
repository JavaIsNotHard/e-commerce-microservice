package com.example.authorization_server.auth;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServices {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;

    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SecurityUser registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            return null;
        }
        Authority authority = authorityRepository.findByName("ROLE_USER")
                .orElseGet(() -> authorityRepository.save(new Authority(null, "ROLE_USER")));
        SecurityUser newUser = new SecurityUser();
        newUser.setUsername(userRequestDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        newUser.setEnabled(true);
        newUser.setAuthorities(Set.of(authority));
        return userRepository.save(newUser);
    }

    @Transactional
    public SecurityUser findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

}
