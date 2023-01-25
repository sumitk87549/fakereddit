package com.fakereddit.demo.security;

import com.fakereddit.demo.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {
    private static final String ENCRYPTION_KEY = "28482B4D6251655468576D5A7134743777397A24432646294A404E635266556A";

    public String getUsernameFromJwtToken(String jwtToken){
        Claims claims = parser().setSigningKey(getPrivateKey()).parseClaimsJws(jwtToken).getBody();
        return claims.getSubject();
    }


    public boolean validateToken(String jwtToken){
        parser()
                .setSigningKey(getPrivateKey())
                .parseClaimsJws(jwtToken).getBody();
        return true;
    }

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() {
        byte[] key = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(key);
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
