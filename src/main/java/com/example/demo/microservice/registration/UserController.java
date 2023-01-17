package com.example.demo.microservice.registration;

import com.example.demo.microservice.registration.dto.UserRequestDTO;
import com.example.demo.microservice.registration.exception.UsernameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class UserController {
    private final RegistrationUserService registrationUserService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            registrationUserService.create(userRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (IllegalArgumentException | UsernameAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
