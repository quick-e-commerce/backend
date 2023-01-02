package com.example.demo.service;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.entity.UserEntityRepository;
import com.example.demo.domain.entity.UserLoginEntity;
import com.example.demo.domain.entity.UserLoginEntityRepository;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserPasswordMismatchException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserAuthorizationService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private UserLoginEntityRepository userLoginEntityRepository;

    private final JsonWebTokenService jwtService = new JsonWebTokenService();

    /**
     * <p>사용자 인증에 필요한 토큰을 발행합니다.</p>
     * <ul>
     *     <li><i>Note</i>: 로그인 수단으로 사용될 수 있습니다.</li>
     * </ul>
     *
     * @param username 사용자명
     * @param password 비밀번호
     * @return 새로 생성된 사용자 인증 토큰을 반환합니다.
     * @throws UserNotFoundException         주어진 이름을 사용하는 사용자가 존재하지 않을 때 발생합니다.
     * @throws UserPasswordMismatchException 주어진 비밀번호가 해당하는 이름의 사용자 비밀번호와 일치하지 않을 때 발생합니다.
     */
    public String createAccessToken(String username, String password) throws UserNotFoundException, UserPasswordMismatchException {
        authenticate(username, password);
        String token = jwtService.create(username);
        applyToDatabase(token);
        return token;
    }

    private void authenticate(String username, String password) throws UserNotFoundException, UserPasswordMismatchException {
        String actualPassword = ensureFindUserEntity(username).getPassword();
        if (!actualPassword.equals(password)) {
            throw new UserPasswordMismatchException();
        }
    }

    private void applyToDatabase(String token) throws UserNotFoundException {
        Claims payload = jwtService.parse(token);
        UserEntity userEntity = ensureFindUserEntity(payload.getAudience());
        UserLoginEntity userLoginEntity = userLoginEntityRepository.findById(userEntity.getId())
                .orElseGet(() -> UserLoginEntity.builder()
                        .userId(userEntity.getId())
                        .user(userEntity)
                        .token(token)
                        .expiresAt(toLocalDateTime(payload.getExpiration()))
                        .build());
        userLoginEntityRepository.save(userLoginEntity);
    }

    /**
     * <p>토큰을 파기합니다.</p>
     * <p>
     * 입력된 토큰은 더 이상 인증 수단으로서의 효과를 잃습니다.
     * 재인증을 위해서는 새로운 토큰을 발급하여 사용해야 합니다.
     * </p>
     * <ul>
     *     <li><i>Note</i>: 로그아웃 수단으로 사용될 수 있습니다.</li>
     * </ul>
     *
     * @param accessToken 사용자 인증 토큰
     * @throws UserNotFoundException 주어진 이름을 사용하는 사용자가 존재하지 않을 때 발생합니다.
     */
    public Void revokeToken(String accessToken) throws UserNotFoundException {
        String username = jwtService.parse(accessToken).getAudience();
        Integer userId = ensureFindUserEntity(username).getId();
        userLoginEntityRepository.deleteById(userId);
        return null;
    }

    private UserEntity ensureFindUserEntity(String username) throws UserNotFoundException {
        return findUserEntity(username).orElseThrow(UserNotFoundException::new);
    }

    private Optional<UserEntity> findUserEntity(String username) {
        return userEntityRepository.findByUsername(username);
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
