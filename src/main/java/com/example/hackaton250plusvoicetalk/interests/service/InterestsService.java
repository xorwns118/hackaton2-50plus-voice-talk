package com.example.hackaton250plusvoicetalk.interests.service;

import com.example.hackaton250plusvoicetalk.interests.persist.InterestsRepository;
import com.example.hackaton250plusvoicetalk.interests.persist.entity.InterestsEntity;
import com.example.hackaton250plusvoicetalk.interests.web.vo.InterestsRequest;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestsService {
    private final InterestsRepository interestsRepository;

    public List<InterestsEntity> getAll(){
        return interestsRepository.findAll().stream().toList();
    }
}
