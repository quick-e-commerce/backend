package com.example.demo.microservice.authorization.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserLogoutDTO {
    @NonNull
    private String accessToken;
}
