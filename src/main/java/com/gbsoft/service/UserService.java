package com.gbsoft.service;

import com.gbsoft.domain.User;
import com.gbsoft.dto.UserFindForm;
import com.gbsoft.dto.UserForm;
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
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public String join(UserForm form){
        User user = createUser(form);

        if(validateDuplicateUser(user.getWriterId())){
            userRepository.save(user);
            return user.getWriterId();
        } else {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private User createUser(UserForm form) {
        User user = new User();

        user.setWriterId(AesEncDec.encrypt(form.getWriterId()));
        user.setWriterPwd(AesEncDec.encrypt(form.getWriterPwd()));
        user.setWriterName(AesEncDec.encrypt(form.getWriterName()));
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    private boolean validateDuplicateUser(String writerId) {
        boolean result = false;
        List<User> users = userRepository.findByWriterId(writerId);
        if(users.isEmpty()) {
            result = true;
        }
        return result;
    }

    public List<User> findAllUsers(){
        List<User> users = userRepository.findAll();
        for(User m : users){
            m.setWriterId(AesEncDec.decrypt(m.getWriterId()));
            m.setWriterName(AesEncDec.decrypt(m.getWriterName()));
        }
        return users;
    }

    public List<User> findUsers(UserFindForm form) {
        List<User> users = new ArrayList<>();
        String keyword = (form.getKeyword()==null || form.getKeyword()=="") ? "" : AesEncDec.encrypt(form.getKeyword());

        if("writerId".equals(form.getSearchType())){
            users = userRepository.findByWriterId(keyword);
        } else if("writerName".equals(form.getSearchType())) {
            users = userRepository.findByName(keyword);
        } else {
            throw new IllegalStateException("지원하지 않는 검색조건 입니다.");
        }

        for(User m : users){
            m.setWriterId(AesEncDec.decrypt(m.getWriterId()));
            m.setWriterName(AesEncDec.decrypt(m.getWriterName()));
        }

        return users;
    }
}
