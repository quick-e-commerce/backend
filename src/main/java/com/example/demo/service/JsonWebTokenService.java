package com.example.demo.service;

import io.jsonwebtoken.Claims;

public class JsonWebTokenService {
    /**
     * 사용자명을 입력 받아 인증 토큰을 생성합니다.
     *
     * @param username 사용자명
     * @return 생성된 JWTs를 반환합니다.
     */
    public String create(String username) {
        // TODO
        return null;
    }

    /**
     * 사용자의 인증 토큰을 입력 받아 디코딩한 정보를 반환합니다.
     *
     * @param jwts 사용자의 JWTs
     * @return 디코딩 된 Payload 정보를 반환합니다.
     */
    public Claims parse(String jwts) {
        // TODO
        return null;
    }
}
