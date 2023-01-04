package com.example.demo.microservice.authorization.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
public class UserAccessTokenDTO {
    @NonNull
    private UserDTO user;

    @NonNull
    private AccessTokenDTO accessToken;
}
