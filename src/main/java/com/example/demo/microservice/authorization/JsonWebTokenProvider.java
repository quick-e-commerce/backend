package com.example.demo.microservice.authorization;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JsonWebTokenProvider {
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * <p>특정 대상에 대한 JSON Web Token을 생성합니다.</p>
     *
     * @param audience   대상의 고유 식별자
     * @param expiration 토큰의 만료 시각
     * @return 생성된 토큰 문자열을 반환합니다.
     */
    public String create(String audience, LocalDateTime expiration) {
        Claims payload = Jwts.claims()
                .setAudience(audience)
                .setIssuedAt(toDate(LocalDateTime.now()))
                .setExpiration(toDate(expiration));
        return Jwts.builder()
                .setClaims(payload)
                .signWith(SECRET_KEY)
                .compact();
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
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(jwts)
                .getBody();
    }
}
