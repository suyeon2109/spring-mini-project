package com.gbsoft.repository;

import com.gbsoft.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public List<User> findByName(String writerName){
        return em.createQuery("select u from User u where u.writerName = :writerName", User.class)
                .setParameter("writerName", writerName)
                .getResultList();
    }

//    public Optional<User> findByWriterId(String writerId){
//        try{
//            User user = em.createQuery("select u from User u where u.writerId = :writerId", User.class)
//                    .setParameter("writerId", writerId)
//                    .getSingleResult();
//            return Optional.of(user);
//        } catch (NoResultException e) {
//            return Optional.empty();
//        }
//    }

    public List<User> findByWriterId(String writerId){
        return em.createQuery("select u from User u where u.writerId = :writerId", User.class)
                .setParameter("writerId", writerId)
                .getResultList();
    }
}
