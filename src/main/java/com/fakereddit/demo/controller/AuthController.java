package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.AuthenticationResponseDto;
import com.fakereddit.demo.dto.LoginRequestDto;
import com.fakereddit.demo.dto.RefreshTokenRequestDto;
import com.fakereddit.demo.dto.RegisterRequestDto;
import com.fakereddit.demo.service.AuthService;
import com.fakereddit.demo.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequestDto registerRequestDto){
        try{
            authService.signup(registerRequestDto);
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
    public AuthenticationResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponseDto refreshTokens(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return authService.refreshToken(refreshTokenRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        refreshTokenService.deleteRefreshedToken(refreshTokenRequestDto.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted... User Successfully logged out!!!");
    }
}
