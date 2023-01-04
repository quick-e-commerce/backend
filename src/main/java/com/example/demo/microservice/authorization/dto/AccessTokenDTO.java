package com.example.demo.microservice.authorization.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
public class AccessTokenDTO {
    @NonNull
    private UserDTO user;

    @NonNull
    private String accessToken;

    @NonNull
    private LocalDateTime expiration;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
