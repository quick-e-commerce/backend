package com.example.demo.microservice.registration.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private LocalDateTime createdAt;

}
