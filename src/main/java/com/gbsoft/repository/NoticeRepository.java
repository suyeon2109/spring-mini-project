package com.gbsoft.repository;

import com.gbsoft.domain.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.awt.print.Pageable;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {
    private final EntityManager em;

    public Long save(Notice notice){
        em.persist(notice);
        return notice.getId();
    }

    public List<Notice> findAll(int startNum) {
        return em.createQuery("select n from Notice n order by n.createdAt desc", Notice.class)
                .setFirstResult(startNum)
                .setMaxResults(10)
                .getResultList();
    }

    public List<Notice> findByTitle(String keyword, int startNum) {
        return em.createQuery("select n from Notice n where n.title like :title order by n.createdAt desc", Notice.class)
                .setParameter("title", "%"+keyword+"%")
                .setFirstResult(startNum)
                .setMaxResults(10)
                .getResultList();
    }

    public Notice findById(Long id){
        return em.find(Notice.class, id);
    }

    public void delete(Notice notice) {
        em.remove(notice);
    }
}
