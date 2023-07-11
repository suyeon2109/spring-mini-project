package com.gbsoft.repository;

import com.gbsoft.domain.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LoginRepository {
    private final EntityManager em;

    public List<UserToken> findByWriterId(String writerId){
        return em.createQuery("select ut from UserToken ut where ut.user.writerId = :writerId", UserToken.class)
                .setParameter("writerId", writerId)
                .getResultList();
    }

    public List<UserToken> findByToken(String token){
        return em.createQuery("select ut from UserToken ut join User u on ut.user.writerId= u.writerId where ut.accessToken = :accessToken or ut.refreshToken = :refreshToken order by ut.id desc", UserToken.class)
                .setParameter("accessToken", token)
                .setParameter("refreshToken", token)
                .getResultList();
    }

    public UserToken save(UserToken userToken) {
        em.persist(userToken);
        return userToken;
    }

    public UserToken update(Long seq, UserToken newToken) {
        UserToken originToken = em.find(UserToken.class, seq);
        originToken.setAccessToken(newToken.getAccessToken());
        originToken.setRefreshToken(newToken.getRefreshToken());

        return originToken;
    }

    public void delete(UserToken token) {
        em.remove(token);
    }
}
