package com.example.demo.domain.dto;

import com.example.demo.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Builder
@Data
public class UserDTO {
    public static UserDTO from(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }

    @NonNull
    private Integer id;

    @NonNull
    private String username;

    @NonNull
    private LocalDateTime createdAt;
}
