package com.example.demo.microservice.authorization;

import com.example.demo.database.User;
import com.example.demo.database.UserAccessTokenRepository;
import com.example.demo.database.UserRepository;
import com.example.demo.microservice.authorization.dto.AccessTokenDTO;
import com.example.demo.microservice.authorization.dto.UserDTO;
import com.example.demo.microservice.authorization.dto.UserLoginDTO;
import com.example.demo.microservice.authorization.dto.UserLogoutDTO;
import com.example.demo.microservice.authorization.exception.UserNotFoundException;
import com.example.demo.microservice.authorization.exception.UserPasswordMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccessTokenRepository userAccessTokenRepository;

    private final JsonWebTokenProvider jwtProvider = new JsonWebTokenProvider();

    /**
     * <p>사용자로 로그인 합니다.</p>
     * <p>
     * 이 동작은 새로운 사용자 인증 정보를 발행합니다.
     * 이전에 발행된 엑세스 토큰은 더 이상 인증 수단으로서의 효과를 잃습니다.
     * </p>
     *
     * @param userLoginDTO 사용자명과 비밀번호에 대한 정보가 포함된 객체
     * @return 사용자 정보와 엑세스 토큰이 포함된 객체를 반환합니다.
     * @throws IllegalArgumentException      사용자명 혹은 비밀번호가 {@code null} 이거나 빈 문자열, 혹은 공백일 때 발생합니다.
     * @throws UserNotFoundException         사용자명에 해당하는 사용자가 없을 때 발생합니다.
     * @throws UserPasswordMismatchException 사용자의 비밀번호가 일치하지 않을 때 발생합니다.
     */
    public AccessTokenDTO login(UserLoginDTO userLoginDTO) throws IllegalArgumentException, UserNotFoundException, UserPasswordMismatchException {
        checkValidOrThrow(userLoginDTO);
        UserDTO userDTO = getUserOrThrow(userLoginDTO);
        return createAccessToken(userDTO);
    }

    private void checkValidOrThrow(UserLoginDTO userLoginDTO) throws IllegalArgumentException {
        if (userLoginDTO == null) {
            throw new IllegalArgumentException();
        }
        if (userLoginDTO.getUsername().isEmpty() || userLoginDTO.getUsername().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (userLoginDTO.getPassword().isEmpty() || userLoginDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    private UserDTO getUserOrThrow(UserLoginDTO userLoginDTO) throws UserNotFoundException, UserPasswordMismatchException {
        try {
            User user = userRepository.findByUsername(userLoginDTO.getUsername()).orElseThrow();
            assert user.getPassword().equals(userLoginDTO.getPassword());
            return UserDTO.from(user);
        } catch (NoSuchElementException e) {
            throw new UserNotFoundException();
        } catch (AssertionError e) {
            throw new UserPasswordMismatchException();
        }
    }

    private AccessTokenDTO createAccessToken(UserDTO userDTO) {
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(5);
        String accessToken = jwtProvider.create(userDTO.getUsername(), expiration);
        return AccessTokenDTO.builder()
                .user(userDTO)
                .accessToken(accessToken)
                .expiration(expiration)
                .build();
    }

    /**
     * <p>사용자를 로그아웃 시킵니다.</p>
     * <p>
     * 이 동작은 사용자 인증 정보를 파기합니다.
     * 기존에 발행된 엑세스 토큰은 더 이상 인증 수단으로서의 효과를 잃습니다.
     * 새로운 유효한 엑세스 토큰을 발급받기 위해서는 다시 로그인해야 합니다.
     * </p>
     *
     * @param userLogoutDTO 로그아웃 할 사용자의 엑세스 토큰이 포함된 객체
     * @throws IllegalArgumentException 엑세스 토큰이 {@code null} 이거나 빈 문자열일 때 발생합니다.
     */
    public Void logout(UserLogoutDTO userLogoutDTO) throws IllegalArgumentException {
        // TODO: Implement this method
        throw new RuntimeException("This method is not implemented yet");
    }
}
