package com.example.demo.microservice.authorization;

import com.example.demo.microservice.authorization.dto.AccessTokenDTO;
import com.example.demo.microservice.authorization.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDTO> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        try {
            UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                    .username(username)
                    .password(password)
                    .build();
            AccessTokenDTO accessTokenDTO = authorizationService.login(userLoginDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(accessTokenDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
