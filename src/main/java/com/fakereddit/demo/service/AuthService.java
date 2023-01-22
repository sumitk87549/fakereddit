package com.fakereddit.demo.service;

import com.fakereddit.demo.dto.RegisterRequest;
import com.fakereddit.demo.model.User;
import com.fakereddit.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

//    @Autowired
    private final PasswordEncoder secureEncoder;

//    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public void signup(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(secureEncoder.encode(request.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
    }

}
