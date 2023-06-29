package com.gbsoft.service;

import com.gbsoft.domain.Notice;
import com.gbsoft.domain.User;
import com.gbsoft.dto.NoticeFindForm;
import com.gbsoft.dto.NoticeForm;
import com.gbsoft.dto.UserFindForm;
import com.gbsoft.dto.UserForm;
import com.gbsoft.repository.NoticeRepository;
import com.gbsoft.repository.UserRepository;
import com.gbsoft.util.AesEncDec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;
    @Transactional
    public Long create(NoticeForm form){
        Notice notice = createNotice(form);
        return noticeRepository.save(notice);
    }

    private Notice createNotice(NoticeForm form) {
        Notice notice = new Notice();

        notice.setTitle(form.getTitle());
        notice.setContent(form.getContent());
        notice.setNote(form.getNote());
        notice.setCreatedWriterId(AesEncDec.encrypt(form.getLoginUserId()));
        notice.setCreatedAt(LocalDateTime.now());

        return notice;
    }

    public List<Notice> findAllNotice() {
        List<Notice> noticeList = noticeRepository.findAll();
        for(Notice n : noticeList){
            n.setCreatedWriterId(AesEncDec.decrypt(n.getCreatedWriterId()));
            if(!(n.getModifiedWriterId() == null)) {
                n.setModifiedWriterId(AesEncDec.decrypt(n.getModifiedWriterId()));
            }
        }
        return noticeList;
    }

    public List<Notice> searchNotice(NoticeFindForm form) {
        List<Notice> noticeList = new ArrayList<>();

        if(form.getSearchType().equals("title")){
            noticeList = noticeRepository.findByTitle(form.getKeyword());
        }

        for(Notice n : noticeList){
            n.setCreatedWriterId(AesEncDec.decrypt(n.getCreatedWriterId()));
            if(!(n.getModifiedWriterId() == null)) {
                n.setModifiedWriterId(AesEncDec.decrypt(n.getModifiedWriterId()));
            }
        }

        for(Notice n : noticeList){
            log.info(n.getTitle());
        }

        return noticeList;
    }
}
