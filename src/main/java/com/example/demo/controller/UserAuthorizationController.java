package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserPasswordMismatchException;
import com.example.demo.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UserAuthorizationController {
    private final Logger logger = Logger.getLogger(UserAuthorizationController.class.getName());

    @Autowired
    UserAuthorizationService userAuthorizationService;

    @PostMapping("/auth")
    public ResponseEntity<String> createAccessToken(
            @RequestParam String username,
            @RequestParam String password
    ) {
        logger.info("user login request as <" + username + ">.");
        try {
            String token = userAuthorizationService.createAccessToken(username, password);
            logger.info("user login as <" + username + ">.");
            return ResponseEntity.status(201).body(token);
        } catch (UserNotFoundException e) {
            logger.warning("user failed to login as <" + username + ">.");
            return ResponseEntity.status(401).body("user not exist");
        } catch (UserPasswordMismatchException e) {
            logger.warning("user failed to login as <" + username + ">.");
            return ResponseEntity.status(401).body("password mismatch");

        }
    }
}
