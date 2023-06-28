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
    public Long join(UserForm form){
        User user = createUser(form);

        if(validateDuplicateUser(user.getWriterId())){
            userRepository.save(user);
            return user.getId();
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

    public List<User> searchUsers(UserFindForm form) {
        List<User> users = new ArrayList<>();

        if(form.getSearchType().equals("writerId")){
            users = userRepository.findByWriterId(AesEncDec.encrypt(form.getKeyword()));
        } else {
            users = userRepository.findByName(AesEncDec.encrypt(form.getKeyword()));
        }

        for(User m : users){
            m.setWriterId(AesEncDec.decrypt(m.getWriterId()));
            m.setWriterName(AesEncDec.decrypt(m.getWriterName()));
        }

        return users;
    }
}
