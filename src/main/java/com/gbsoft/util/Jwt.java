package com.gbsoft.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class Jwt {
    /**
     * body 가 들어간 토큰 생성
     *
     * @param body
     * @param expired 토근 만료 시간
     * @return
     */
    private static final String secret = "jwtSecretWords";
    public static String token(Map<String, Object> body, Optional<LocalDateTime> expired) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);

        Key key = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setHeader(new HashMap<>())
                .setClaims(body)
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(2)))
                .signWith(SignatureAlgorithm.HS512, key);

        // 만료시간을 설정할 경우 expir 설정
        expired.ifPresent(exp -> {
            builder.setExpiration(Timestamp.valueOf(exp));
        });

        return builder.compact();
    }

    /**
     * 기본 만료시간 : 하루 30분 : LocalDateTime.now().plusMinutes(30)
     * 1시간 : LocalDateTime.now().plusHours(1)
     *
     * @param body
     * @return
     */
    public static String token(Map<String, Object> body) {
        return token(body, Optional.of(LocalDateTime.now().plusMinutes(30)));
    }

    /**
     * 토큰 검증후 저장된 값 복원
     */
    public static Map<String, Object> verify(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token)
                .getBody();

        return new HashMap<>(claims);
    }

    /**
     * 토큰의 유효성 + 만료일자 확인
     */
    public static boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
