package com.fakereddit.demo.service;

import com.fakereddit.demo.exceptions.SpringRedditException;
import com.fakereddit.demo.model.RefreshToken;
import com.fakereddit.demo.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshedToken(String token){
        refreshTokenRepository.findByToken(token).orElseThrow(()->new SpringRedditException("Following Refresh Token not found: "+token));
    }
    public void deleteRefreshedToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
