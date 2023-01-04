package com.example.demo.microservice.authorization.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessTokenDTO {
    private UserDTO user;
    private String accessToken;
    private LocalDateTime createdAt;
    private LocalDateTime expiresIn;
}
