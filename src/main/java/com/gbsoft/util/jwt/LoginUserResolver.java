package com.gbsoft.util.jwt;

import com.gbsoft.domain.LoginUser;
import com.gbsoft.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("resolveArgument");
        final Map<String, Object> resolved = new HashMap<>();

        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();

        // 쿠키에 토큰이 있는 경우 꺼내서 verify 후 로그인 정보 리턴
        Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(LoginCheckInterceptor.COOKIE_NAME))
                .map(Cookie::getValue).findFirst().ifPresent(token -> {
                    Map<String, Object> info = new HashMap<>(Jwt.verify(token));

                    // @LoginUser String writerId, @LoginUser String writerPwd, @LoginUser String writerName
                    if (parameter.getParameterType().isAssignableFrom(String.class)) {
                        resolved.put("resolved", info.get(parameter.getParameterName()));
                    }
                    // @LoginUser User user
                    else if (parameter.getParameterType().isAssignableFrom(User.class)) {
                        User user = User.builder()
                                .writerId((String) info.get("writerId"))
                                .build();

                        resolved.put("resolved", user);
                    }
                });

        return resolved.get("resolved");
    }
}
