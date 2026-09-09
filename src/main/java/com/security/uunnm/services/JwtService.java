package com.security.uunnm.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey() {
        byte[] bytesSecretKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytesSecretKey);
    }

    public String getUsernameByToken(String token) {
        return extraClaim(token, Claims::getSubject);
    }

    private <T> T extraClaim(String token, Function<Claims, T> resolve) {
        final Claims claims = extraAllClaims(token);
        return resolve.apply(claims);
    }

    private Claims extraAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(UserDetails userDetails, String token) {
        String username = getUsernameByToken(token);
        return username.equals(userDetails.getUsername());
    }
}
