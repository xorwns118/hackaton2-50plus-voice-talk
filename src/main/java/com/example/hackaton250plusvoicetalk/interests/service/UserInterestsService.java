package com.example.hackaton250plusvoicetalk.interests.service;

import com.example.hackaton250plusvoicetalk.interests.persist.UserInterestsRepository;
import com.example.hackaton250plusvoicetalk.interests.persist.entity.UserInterestsEntity;
import com.example.hackaton250plusvoicetalk.interests.persist.entity.UserInterestsPK;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInterestsService {
    private final UserInterestsRepository userInterestsRepository;

    public UserInterestsEntity addInterest(Long userId, Long interestId) {
        UserInterestsPK userInterestsPK = new UserInterestsPK(userId, interestId); // UserInterestsPK 초기화
        UserInterestsEntity userInterestsEntity = UserInterestsEntity.builder()
                .userInterestsPK(userInterestsPK)
                .build();

        return userInterestsRepository.save(userInterestsEntity);
    }

    public void removeInterest(Long userId, Long interestId) {
        UserInterestsPK userInterestsPK = new UserInterestsPK(userId, interestId);
        userInterestsRepository.deleteById(userInterestsPK);
    }

    public List<UserInterestsEntity> getUserInterests(Long userId) {
        return userInterestsRepository.findAll().stream().toList();
    }
}
