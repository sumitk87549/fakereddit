package com.fakereddit.demo.security;

import com.fakereddit.demo.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.security.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
@Slf4j
public class JwtProvider {
    private static final String ENCRYPTION_KEY = "28482B4D6251655468576D5A7134743777397A24432646294A404E635266556A";

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;


    public String getUsernameFromJwtToken(String jwtToken){
        Claims claims = parser().setSigningKey(getPrivateKey()).parseClaimsJws(jwtToken).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String jwtToken){
        try {
            parser().setSigningKey(getPrivateKey())
                    .parseClaimsJws(jwtToken).getBody();
            return true;
        } catch (Exception e) {
            log.error("Unable to verify jwt token - " + jwtToken);
            e.printStackTrace();
        }
        return false;
    }

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }
    public String generateTokenWithUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private Key getPrivateKey() {
        byte[] key = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }


//  ------------- Implementation by Programming Techie --------------
//    @PostConstruct
//    public void init(){
//        try{
//            keyStore = KeyStore.getInstance("JKS");
//            InputStream resourceAsStream = getClass().getResourceAsStream("/frkey.jks");
//            keyStore.load(resourceAsStream,"pass123".toCharArray());
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private KeyStore keyStore;

//    private PrivateKey getPrivateKey() {
//        try {
//            return (PrivateKey) keyStore.getKey("frkey","pass123".toCharArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SpringRedditException("Unable to retrieve key form keystore!");
//        }
//    }
}
