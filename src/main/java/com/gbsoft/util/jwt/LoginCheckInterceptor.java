package com.gbsoft.util.jwt;

import com.gbsoft.domain.User;
import com.gbsoft.service.LoginService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    public static final String COOKIE_NAME = "login_token";
    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ModelAndViewDefiningException {
        String token = getTokenFromCookie(LoginCheckInterceptor.COOKIE_NAME, request);
        token = loginService.validateToken(token, response);
        log.info("token : {}", token);

        try {
            Map<String, Object> info = new HashMap<>(Jwt.verify(token));

            User user = User.builder()
                    .writerId((String) info.get("writerId"))
                    .build();

            // view 에서 login.writerId 로 접근가능
            request.setAttribute("login", user);
            ModelAndView mav = new ModelAndView("/userHome");
        } catch (JwtException ex) {
            log.error(ex.getMessage());

            deleteCookie(response);
            loginService.deleteToken(token);
            ModelAndView mav = new ModelAndView("/error");
            throw new ModelAndViewDefiningException(mav);
        }

        return true;
    }

    private static String getTokenFromCookie(String cookieName, HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst().map(Cookie::getValue)
                .orElse("dummy");
        return token;
    }

    private static void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("login_token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
