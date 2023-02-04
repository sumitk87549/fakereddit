package com.fakereddit.demo.service;

import com.fakereddit.demo.dto.AuthenticationResponseDto;
import com.fakereddit.demo.dto.LoginRequestDto;
import com.fakereddit.demo.dto.RefreshTokenRequestDto;
import com.fakereddit.demo.dto.RegisterRequestDto;
import com.fakereddit.demo.exceptions.SpringRedditException;
import com.fakereddit.demo.model.NotificationEmail;
import com.fakereddit.demo.model.User;
import com.fakereddit.demo.model.VerificationToken;
import com.fakereddit.demo.repository.UserRepository;
import com.fakereddit.demo.repository.VerificationTokenRepository;
import com.fakereddit.demo.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder secureEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public User getCurrentUser(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("GET PRINCIPAL AUTHENTICATION ------>>>> "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return  userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UsernameNotFoundException("No user found for principal - "+principal));
        //CHECK LATER MIGHT THROW ERROR
    }


    public void signup(RegisterRequestDto request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(secureEncoder.encode(request.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendEmail(new NotificationEmail("Please Activate your Account.",user.getEmail(),"Thank you for signing up to Fake Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token){
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Verification token invalid."));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        User userToBeActivated = userRepository.findByUsername(verificationToken.getUser().getUsername()).orElseThrow(()->new SpringRedditException("User not found in record!"));
        userToBeActivated.setEnabled(true);
        userRepository.save(userToBeActivated);
    }

    public AuthenticationResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authenticationObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticationObject);
        String authenticationToken = jwtProvider.generateToken(authenticationObject);
        return AuthenticationResponseDto.builder()
                .authenticationToken(authenticationToken)
                .username(loginRequestDto.getUsername())
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expirationTime(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .build();
    }

    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        refreshTokenService.validateRefreshedToken(refreshTokenRequestDto.getRefreshToken());
        return AuthenticationResponseDto.builder()
                .username(refreshTokenRequestDto.getUsername())
                .authenticationToken(jwtProvider.generateTokenWithUsername(refreshTokenRequestDto.getUsername()))
                .refreshToken(refreshTokenRequestDto.getRefreshToken())
                .expirationTime(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .build();
    }

    public boolean isLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
