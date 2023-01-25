package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.AuthenticationResponse;
import com.fakereddit.demo.dto.LoginRequest;
import com.fakereddit.demo.dto.RegisterRequest;
import com.fakereddit.demo.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
        try{
            authService.signup(registerRequest);
            return new ResponseEntity<>("User Registration Successful", HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Unable to Register user", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity verifyAccount(@PathVariable String token){
        try {
            authService.verifyAccount(token);
            log.info("Account verified Successfully...");
            return new ResponseEntity("Account Verified Successfully...",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error While verifying Account for token - "+token+"\n"+e);
            return new ResponseEntity<>("Unable to verify Account!",HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
