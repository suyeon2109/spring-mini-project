package com.gbsoft.repository;

import com.gbsoft.domain.Member;
import com.gbsoft.util.AesEncDec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String writerName){
        return em.createQuery("select m from Member m where m.writerName = :writerName", Member.class)
                .setParameter("writerName", writerName)
                .getResultList();
    }

    public Optional<Member> findByWriterId(String writerId){
        try{
            Member member = em.createQuery("select m from Member m where m.writerId = :writerId", Member.class)
                    .setParameter("writerId", writerId)
                    .getSingleResult();
            return Optional.of(member);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}
