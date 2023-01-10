package com.example.demo.microservice.authorization;

import com.example.demo.microservice.authorization.dto.UserAccessTokenDTO;
import com.example.demo.microservice.authorization.dto.UserLoginDTO;
import com.example.demo.microservice.authorization.exception.UserNotFoundException;
import com.example.demo.microservice.authorization.exception.UserPasswordMismatchException;
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
    public ResponseEntity<UserAccessTokenDTO> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        try {
            UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                    .username(username)
                    .password(password)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(authorizationService.login(userLoginDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (UserPasswordMismatchException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
