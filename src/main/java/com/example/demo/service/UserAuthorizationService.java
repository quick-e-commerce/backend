package com.example.demo.service;

import com.example.demo.domain.dto.UserLoginDTO;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.entity.UserEntityRepository;
import com.example.demo.domain.entity.UserLoginEntity;
import com.example.demo.domain.entity.UserLoginEntityRepository;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserPasswordMismatchException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private JsonWebTokenService jwtService;

    /**
     * <p>사용자 인증에 필요한 토큰이 포함된 인증정보를 발행합니다.</p>
     * <ul>
     *     <li><i>Note</i>: 로그인 수단으로 사용될 수 있습니다.</li>
     * </ul>
     *
     * @param username 사용자명
     * @param password 비밀번호
     * @return 사용자 정보가 포함된 사용자 인증정보를 반환합니다. 새로운 엑세스 토큰이 발행됩니다.
     * @throws UserNotFoundException         주어진 이름을 사용하는 사용자가 존재하지 않을 때 발생합니다.
     * @throws UserPasswordMismatchException 주어진 비밀번호가 해당하는 이름의 사용자 비밀번호와 일치하지 않을 때 발생합니다.
     */
    public UserLoginDTO authenticate(String username, String password) throws UserNotFoundException, UserPasswordMismatchException, IllegalArgumentException {
        checkUsernamePassword(username, password);
        String newAccessToken = jwtService.create(username);
        UserLoginEntity userLoginEntity = createUserLoginEntity(newAccessToken);
        userLoginEntityRepository.saveAndFlush(userLoginEntity);
        return UserLoginDTO.from(userLoginEntity);
    }

    private void checkUsernamePassword(String username, String password) throws UserNotFoundException, UserPasswordMismatchException, IllegalArgumentException {
        if (!(StringUtils.hasText(username) && StringUtils.hasText(password))) {
            throw new IllegalArgumentException();
        }
        String actualPassword = ensureFindUserEntity(username).getPassword();
        if (!actualPassword.equals(password)) {
            throw new UserPasswordMismatchException();
        }
    }

    private UserLoginEntity createUserLoginEntity(String accessToken) throws UserNotFoundException {
        Claims payload = jwtService.parse(accessToken);
        UserEntity userEntity = ensureFindUserEntity(payload.getAudience());
        return UserLoginEntity.builder()
                .userId(userEntity.getId())
                .user(userEntity)
                .token(accessToken)
                .expiresAt(toLocalDateTime(payload.getExpiration()))
                .build();
    }

    /**
     * <p>엑세스 토큰이 포함된 사용자 인증 정보를 받아 비어있는 사용자 정보를 채워 반환합니다.</p>
     * <p>유효하지 않은 토큰에 대하여 예외를 발생시킵니다..</p>
     * <ul>
     *     <li><i>Note</i>: 로그인 수단이 필요하다면 {@link UserAuthorizationService#authenticate(String, String)}를 살펴보십시오.</li>
     * </ul>
     *
     * @param accessToken 비밀번호
     * @return 사용자 정보가 포함된 사용자 인증정보를 반환합니다. 기존의 엑세스 토큰이 재활용됩니다.
     * @throws UserNotFoundException 주어진 이름을 사용하는 사용자가 존재하지 않을 때 발생합니다.
     * @see UserAuthorizationService#authenticate(String, String)
     */
    public UserLoginDTO authenticate(String accessToken) throws UserNotFoundException, IllegalArgumentException {
        if (!isValid(accessToken)) {
            throw new IllegalArgumentException();
        }
        UserLoginEntity userLoginEntity = ensureFindUserLoginEntity(accessToken);
        return UserLoginDTO.from(userLoginEntity);
    }

    private UserLoginEntity ensureFindUserLoginEntity(String accessToken) throws UserNotFoundException {
        Claims payload = jwtService.parse(accessToken);
        UserEntity userEntity = ensureFindUserEntity(payload.getAudience());
        return userLoginEntityRepository.findById(userEntity.getId()).orElseThrow(UserNotFoundException::new);
    }

    /**
     * <p>사용자 인증 정보를 파기합니다.</p>
     * <p>
     * 입력된 인증 정보는 더 이상 인증 수단으로서의 효과를 잃습니다.
     * 재인증을 위해서는 새로운 인증 정보를 발급하여 사용해야 합니다.
     * </p>
     * <ul>
     *     <li><i>Note</i>: 로그아웃 수단으로 사용될 수 있습니다.</li>
     * </ul>
     *
     * @param userLoginDTO 사용자 인증 정보
     * @throws UserNotFoundException 주어진 이름을 사용하는 사용자가 존재하지 않을 때 발생합니다.
     */
    public Void revoke(UserLoginDTO userLoginDTO) throws UserNotFoundException, IllegalArgumentException {
        if (!isValid(userLoginDTO)) {
            throw new IllegalArgumentException();
        }
        Integer userId = ensureFindUserEntity(userLoginDTO).getId();
        userLoginEntityRepository.deleteById(userId);
        return null;
    }

    private Boolean isValid(UserLoginDTO userLoginDTO) {
        return isValid(userLoginDTO.getToken());
    }

    private Boolean isValid(String accessToken) {
        try {
            jwtService.parse(accessToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }

    }

    private UserEntity ensureFindUserEntity(UserLoginDTO userLoginDTO) throws UserNotFoundException {
        return findUserEntity(userLoginDTO).orElseThrow(UserNotFoundException::new);
    }

    private UserEntity ensureFindUserEntity(String username) throws UserNotFoundException {
        return findUserEntity(username).orElseThrow(UserNotFoundException::new);
    }

    private Optional<UserEntity> findUserEntity(UserLoginDTO userLoginDTO) {
        return findUserEntity(getUsername(userLoginDTO));
    }

    private Optional<UserEntity> findUserEntity(String username) {
        return userEntityRepository.findByUsername(username);
    }

    private String getUsername(UserLoginDTO userLoginDTO) {
        return jwtService.parse(userLoginDTO.getToken()).getAudience();
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
