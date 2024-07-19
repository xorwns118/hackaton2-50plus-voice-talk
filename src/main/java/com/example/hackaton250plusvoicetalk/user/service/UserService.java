package com.example.hackaton250plusvoicetalk.user.service;


import com.example.hackaton250plusvoicetalk.user.persist.UserRepository;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import com.example.hackaton250plusvoicetalk.user.web.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User join(String username){
        var entity = UserEntity.builder()
                .username(username)
                .build();
        var saved = userRepository.save(entity);
        return entityToObject(saved);

    }




    private User entityToObject(UserEntity e){
        return User.builder()
                .id(e.getId())
                .build();
    }
}
