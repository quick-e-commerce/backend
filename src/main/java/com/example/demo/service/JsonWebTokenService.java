package com.example.demo.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JsonWebTokenService {
    // TODO: Make secret-key configurable.
    private final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 사용자명을 입력 받아 인증 토큰을 생성합니다.
     *
     * @param username 사용자명
     * @return 생성된 JWTs를 반환합니다.
     */
    public String create(String username) {
        Claims payload = Jwts.claims()
                .setAudience(username)
                .setIssuedAt(createIssuedAt())
                .setExpiration(createExpiration());
        return Jwts.builder()
                .setClaims(payload)
                .signWith(KEY)
                .compact();
    }

    private Date createIssuedAt() {
        return toDate(LocalDateTime.now());
    }

    private Date createExpiration() {
        // TODO: Make token-lifetime configurable.
        return toDate(LocalDateTime.now().plusDays(1));
    }

    private Date toDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 사용자의 인증 토큰을 입력 받아 디코딩한 정보를 반환합니다.
     *
     * @param jwts 사용자의 JWTs
     * @return 디코딩 된 Payload 정보를 반환합니다.
     * @throws SignatureException       토큰 서명이 잘못되었을 시에 발생합니다.
     * @throws MalformedJwtException    토큰이 오염되었을 시에 발생합니다.
     * @throws ExpiredJwtException      토큰이 만료되었을 시에 발생합니다.
     * @throws UnsupportedJwtException  지원되는 JWTs 형식이 아니면 발생합니다.
     * @throws IllegalArgumentException 토큰이 {@code null}이거나 공백이면 발생합니다.
     */
    public Claims parse(String jwts) throws
            SignatureException,
            MalformedJwtException,
            ExpiredJwtException,
            UnsupportedJwtException,
            IllegalArgumentException {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(jwts)
                .getBody();
    }
}
