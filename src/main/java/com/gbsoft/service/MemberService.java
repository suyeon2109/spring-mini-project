package com.gbsoft.service;

import com.gbsoft.domain.Member;
import com.gbsoft.dto.MemberForm;
import com.gbsoft.repository.MemberRepository;
import com.gbsoft.util.AesEncDec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public Long join(MemberForm form){
        Member member = createMember(form);

        if(validateDuplicateMember(member.getWriterId())){
            memberRepository.save(member);
            return member.getId();
        } else {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private Member createMember(MemberForm form) {
        Member member = new Member();

        member.setWriterId(AesEncDec.encrypt(form.getWriterId()));
        member.setWriterPwd(AesEncDec.encrypt(form.getWriterPwd()));
        member.setWriterName(AesEncDec.encrypt(form.getWriterName()));
        member.setCreatedAt(LocalDateTime.now());

        return member;
    }

    private boolean validateDuplicateMember(String writerId) {
        boolean result = false;
        Optional<Member> members = memberRepository.findByWriterId(writerId);
        if(members.equals(Optional.empty())) {
            result = true;
        }
        return result;
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    public List<Member> findByName(String writerName){
        return memberRepository.findByName(writerName);
    }

    public Optional<Member> findByWriterId(String writerId){
        return memberRepository.findByWriterId(writerId);
    }
}
