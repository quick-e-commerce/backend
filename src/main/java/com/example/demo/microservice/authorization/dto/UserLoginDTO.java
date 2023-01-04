package com.example.demo.microservice.authorization.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserLoginDTO {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
