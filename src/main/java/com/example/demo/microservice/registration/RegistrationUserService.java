package com.example.demo.microservice.registration;

import com.example.demo.database.User;
import com.example.demo.database.UserRepository;
import com.example.demo.microservice.registration.dto.UserRequestDTO;
import com.example.demo.microservice.registration.exception.UsernameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(UserRequestDTO userRequestDTO) throws IllegalArgumentException, UsernameAlreadyExistException {
        validate(userRequestDTO);
        User user = userRequestDTO.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    private void validate(UserRequestDTO userDTO) throws IllegalArgumentException, UsernameAlreadyExistException {
        if (userDTO.getUsername().isBlank() || userDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException();
        }
    }

}
