package com.example.demo.domain.dto;

import com.example.demo.domain.entity.UserLoginEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
@Data
public class UserLoginDTO {
    public static UserLoginDTO from(UserLoginEntity entity) {
        return builder()
                .user(UserDTO.from(entity.getUser()))
                .accessToken(entity.getToken())
                .accessTokenExpiresAt(entity.getExpiresAt())
                .build();
    }

    private UserDTO user;

    @NonNull
    private String accessToken;

    @NonNull
    private LocalDateTime accessTokenExpiresAt;
}
