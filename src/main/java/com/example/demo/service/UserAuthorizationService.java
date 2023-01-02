package com.example.demo.service;

public class UserAuthorizationService {
    /**
     * <p>사용자 인증에 필요한 토큰을 발행합니다.</p>
     * <ul>
     *     <li><i>Note</i>: 로그인 수단으로 사용될 수 있습니다.</li>
     * </ul>
     *
     * @param username 사용자명
     * @param password 비밀번호
     * @return 새로 생성된 사용자 인증 토큰을 반환합니다.
     */
    public String createAccessToken(String username, String password) {
        // TODO
        return null;
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
     */
    public Void revokeToken(String accessToken) {
        // TODO
        return null;
    }
}
