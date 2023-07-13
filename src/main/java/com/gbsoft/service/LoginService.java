package com.gbsoft.service;

import com.gbsoft.domain.User;
import com.gbsoft.domain.UserToken;
import com.gbsoft.dto.LoginForm;
import com.gbsoft.repository.LoginRepository;
import com.gbsoft.repository.UserRepository;
import com.gbsoft.util.AesEncDec;
import com.gbsoft.util.jwt.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LoginService {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;

    public User login(LoginForm form) {
        User user = new User();
        user.setWriterId(AesEncDec.encrypt(form.getWriterId()));
        user.setWriterPwd(AesEncDec.encrypt(form.getWriterPwd()));
        List<User> users = userRepository.findByWriterIdAndWriterPwd(user);

        if(users.isEmpty()){
            throw new IllegalStateException("가입되지 않은 회원입니다.");
        } else {
            return users.get(0);
        }
    }

    @Transactional
    public UserToken saveToken(User user) {
        UserToken newToken = new UserToken();
        newToken.setUser(user);
        newToken.setAccessToken(Jwt.token(user, Optional.of(LocalDateTime.now().plusMinutes(30))));
        newToken.setRefreshToken(Jwt.token(user, Optional.of(LocalDateTime.now().plusHours(2))));
        newToken.setUseYn("Y");
        newToken.setCreatedAt(LocalDateTime.now());
        loginRepository.save(newToken);

        return newToken;
    }

    public String validateToken(String token, HttpServletResponse response) {
        List<UserToken> userTokens = loginRepository.findByToken(token);

        if (userTokens.isEmpty()) {
            log.error("UserToken is not exist");
            return "dummy";
        } else {
            String[] tokens = {userTokens.get(0).getAccessToken(), userTokens.get(0).getRefreshToken()};
            String validToken = Arrays.stream(tokens)
                    .filter(arr -> Jwt.validateToken(arr))
                    .findFirst()
                    .orElse(userTokens.get(0).getRefreshToken());

            saveCookie(validToken, response);
            return validToken;
        }
    }

    public HttpServletResponse saveCookie(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie("login_token", token);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        return response;
    }

    @Transactional
    public void deleteToken(String token) {
        List<UserToken> tokenList = loginRepository.findByToken(token);
        for(UserToken u: tokenList){
            loginRepository.delete(u);
        }
    }


    @Transactional
    public void deleteTokenList(User user) {
        List<UserToken> tokenList = loginRepository.findByWriterId(user.getWriterId());
        for(UserToken u: tokenList){
            loginRepository.delete(u);
        }
    }
}
