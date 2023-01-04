package com.example.demo.microservice.authorization.dto;

import com.example.demo.database.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTO {
    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getCreatedAt(), user.getUsername());
    }

    private Integer id;
    private LocalDateTime createdAt;

    private String username;
}
