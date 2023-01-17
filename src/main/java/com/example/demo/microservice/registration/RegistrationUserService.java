package com.example.demo.microservice.registration;

import com.example.demo.database.User;
import com.example.demo.database.UserRepository;
import com.example.demo.microservice.registration.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(UserRequestDTO userRequestDTO) throws Exception {
        validateDuplicateMember(userRequestDTO);
        User user = userRequestDTO.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    private void validateDuplicateMember(UserRequestDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
