package com.gbsoft.repository;

import com.gbsoft.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {
    private final EntityManager em;

    public Long save(Notice notice){
        em.persist(notice);
        return notice.getId();
    }

    public List<Notice> findAll() {
        return em.createQuery("select n from Notice n", Notice.class)
                .getResultList();
    }

    public List<Notice> findByTitle(String keyword) {
        return em.createQuery("select n from Notice n where n.title like :title", Notice.class)
                .setParameter("title", "%"+keyword+"%")
                .getResultList();
    }
}
