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
    public String create(NoticeForm form, String writerId){
        Notice notice = createNotice(form, writerId);
        return noticeRepository.save(notice);
    }

    private Notice createNotice(NoticeForm form, String writerId) {
        Notice notice = new Notice();

        notice.setTitle(form.getTitle());
        notice.setContent(form.getContent());
        notice.setNote(form.getNote());
        notice.setCreatedWriterId(writerId);
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

    public List<Notice> findNotice(NoticeFindForm form, int startIndex, int pageSize) {
        List<Notice> noticeList = new ArrayList<>();
        form.setKeyword(form.getKeyword()==null ? "" : form.getKeyword());

        if("title".equals(form.getSearchType())){
            noticeList = noticeRepository.findByTitle(form, startIndex, pageSize);
        } else {
            throw new IllegalStateException("지원하지 않는 검색조건 입니다.");
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

        for(String s: stringIdList){
            Notice notice = findNoticeById(Long.parseLong(s));
            noticeRepository.delete(notice);
        }
    }
    public int findNoticeCount(NoticeFindForm form) {
        int result = 0;

        if("title".equals(form.getSearchType())){
            result = noticeRepository.findByTitleCount(form);
        }

        return result;
    }

    public Notice findNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id);
        if(notice == null){
            throw new IllegalStateException("공지사항이 존재하지 않습니다.");
        } else {
            return notice;
        }
    }

    @Transactional
    public void update(NoticeForm form, Long id, String writerId) {
        Notice notice = findNoticeById(id);

        notice.setTitle(form.getTitle());
        notice.setContent(form.getContent());
        notice.setNote(form.getNote());
        notice.setModifiedAt(LocalDateTime.now());
        notice.setModifiedWriterId(writerId);
    }
}
