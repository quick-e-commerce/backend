package com.example.demo.controller;

import com.example.demo.domain.dto.UserLoginDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserPasswordMismatchException;
import com.example.demo.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class UserAuthorizationController {
    private final Logger logger = Logger.getLogger(UserAuthorizationController.class.getName());

    @Autowired
    UserAuthorizationService userAuthorizationService;

    @PostMapping("/auth")
    public ResponseEntity<UserLoginDTO> createAccessToken(
            @RequestParam String username,
            @RequestParam String password
    ) {
        logger.info("user login request as <" + username + ">.");
        try {
            UserLoginDTO userLoginDTO = userAuthorizationService.authenticate(username, password);
            logger.info("user login as <" + userLoginDTO.getUser().getUsername() + ">.");
            return ResponseEntity.status(HttpStatus.CREATED).body(userLoginDTO);
        } catch (UserNotFoundException e) {
            logger.warning("user failed to login as <" + username + ">.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (UserPasswordMismatchException e) {
            logger.warning("user failed to login as <" + username + ">.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
