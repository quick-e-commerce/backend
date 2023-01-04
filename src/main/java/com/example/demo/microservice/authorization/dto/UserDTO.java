package com.example.demo.microservice.authorization.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer id;
    private LocalDateTime createdAt;

    private String username;
}
