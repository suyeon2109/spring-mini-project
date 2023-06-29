package com.gbsoft.service;

import com.gbsoft.domain.User;
import com.gbsoft.domain.UserToken;
import com.gbsoft.dto.LoginForm;
import com.gbsoft.dto.UserFindForm;
import com.gbsoft.dto.UserForm;
import com.gbsoft.repository.LoginRepository;
import com.gbsoft.repository.UserRepository;
import com.gbsoft.util.AesEncDec;
import com.gbsoft.util.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LoginService {
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;

    public void login(LoginForm form) {
        User user = new User();
        user.setWriterId(AesEncDec.encrypt(form.getWriterId()));
        user.setWriterPwd(AesEncDec.encrypt(form.getWriterPwd()));
        List<User> users = userRepository.findByWriterIdAndWriterPwd(user);

        if(users.isEmpty()){
            throw new IllegalStateException("가입되지 않은 회원입니다.");
        }
    }

    @Transactional
    public UserToken saveToken(LoginForm form) {
        UserToken newToken = new UserToken();
        Map<String, Object> map = new HashMap<>();
        String writerId = AesEncDec.encrypt(form.getWriterId());
        map.put("writerId", writerId);

        newToken.setWriterId(writerId);
        newToken.setAccessToken(Jwt.token(map, Optional.of(LocalDateTime.now().plusMinutes(1))));
        newToken.setRefreshToken("");
        newToken.setUseYn("Y");
        newToken.setCreatedAt(LocalDateTime.now());

        loginRepository.save(newToken);

        return newToken;
    }

    @Transactional
    public boolean validateAndIssueRefreshToken(String writerId) {
        List<UserToken> userTokens = loginRepository.findByWriterId(writerId);

        if (userTokens.isEmpty()) {
            log.info("UserToken is not exist");
            return false;
        } else if (Jwt.validateToken(userTokens.get(userTokens.size()-1).getAccessToken())) {
            log.info("Access token is valid");
            return true;
        } else if (userTokens.get(userTokens.size()-1).getRefreshToken().equals("")) {
            log.info("RefreshToken is empty. Issue refreshToken.");
            Map<String, Object> map = new HashMap<>();
            map.put("writerId", writerId);

            userTokens.get(userTokens.size()-1).setRefreshToken(Jwt.token(map, Optional.of(LocalDateTime.now().plusMinutes(1))));
            userTokens.get(userTokens.size()-1).setModifiedAt(LocalDateTime.now());
            loginRepository.update(userTokens.get(userTokens.size()-1).getSeq(), userTokens.get(userTokens.size()-1));

            return true;
        } else if(Jwt.validateToken(userTokens.get(userTokens.size()-1).getRefreshToken())){
            log.info("RefreshToken is valid");
            return true;
        } else {
            log.info("Refresh token is not valid. Delete token database.");
            loginRepository.delete(userTokens);
            return false;
        }
    }
}
