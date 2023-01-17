package com.example.demo.microservice.registration.dto;

import com.example.demo.database.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserRequestDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
