package com.example.demo.microservice.registration;

import com.example.demo.microservice.registration.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/registration")
public class UserController {
    @Autowired
    RegistrationUserService registrationUserService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public void signUp(@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        registrationUserService.create(userRequestDTO);
    }
}
