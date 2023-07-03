package com.gbsoft.service;

import com.gbsoft.domain.Notice;
import com.gbsoft.dto.NoticeFindForm;
import com.gbsoft.dto.NoticeForm;
import com.gbsoft.repository.NoticeRepository;
import com.gbsoft.util.AesEncDec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
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

    public int findAllNoticeCount() {
        return noticeRepository.findAllNoticeCount();
    }

    public List<Notice> findAllNotice(int startIndex, int pageSize) {
        List<Notice> noticeList = noticeRepository.findAll(startIndex, pageSize);
        for(Notice n : noticeList){
            n.setCreatedWriterId(AesEncDec.decrypt(n.getCreatedWriterId()));
            if(!(n.getModifiedWriterId() == null)) {
                n.setModifiedWriterId(AesEncDec.decrypt(n.getModifiedWriterId()));
            }
        }
        return noticeList;
    }

    public List<Notice> searchNotice(NoticeFindForm form, int startIndex, int pageSize) {
        List<Notice> noticeList = new ArrayList<>();

        if("title".equals(form.getSearchType())){
            noticeList = noticeRepository.findByTitle(form.getKeyword(), startIndex, pageSize);
        }

        for(Notice n : noticeList){
            n.setCreatedWriterId(AesEncDec.decrypt(n.getCreatedWriterId()));
            if(!(n.getModifiedWriterId() == null)) {
                n.setModifiedWriterId(AesEncDec.decrypt(n.getModifiedWriterId()));
            }
        }

        return noticeList;
    }

    @Transactional
    public void delete(String ids) {
        String[] stringIdList = ids.split(",");
        System.out.println("stringIdList.length"+stringIdList.length);

        for(String s: stringIdList){
            System.out.println(s);
            Notice notice = noticeRepository.findById(Long.parseLong(s));
            noticeRepository.delete(notice);
        }
    }
    public int findNoticeCount(NoticeFindForm form) {
        int result = 0;

        if("title".equals(form.getSearchType())){
            result = noticeRepository.findByTitleCount(form.getKeyword());
        }

        return result;
    }
}
