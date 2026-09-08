package com.security.uunnm.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final String secretKey;

    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator KeyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = KeyGen.generateKey();
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public String getJwt(String username) {
        Map<String, Object> claim = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .and()
                .signWith(this.getKey())
                .compact();
    }

    public Key getKey() {
        byte[] bytesSecretKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytesSecretKey);
    }
}
