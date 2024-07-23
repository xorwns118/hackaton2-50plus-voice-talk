package com.example.hackaton250plusvoicetalk.interests.controller;

import com.example.hackaton250plusvoicetalk.interests.persist.entity.InterestsEntity;
import com.example.hackaton250plusvoicetalk.interests.persist.entity.UserInterestsEntity;
import com.example.hackaton250plusvoicetalk.interests.service.InterestsService;
import com.example.hackaton250plusvoicetalk.interests.web.vo.InterestsRequest;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SessionAttributes("users")
public class InterestsController {
    private final InterestsService interestsService;

    /**
     *
     * @return Get all interests list
     */
    @GetMapping("/interests")
    public ResponseEntity<Api<List<InterestsEntity>>> getInterests(
    ){
        List<InterestsEntity> interestsEntities = interestsService.getAll();

        Api<List<InterestsEntity>> response = Api.<List<InterestsEntity>>builder()
                .resultCode("200")
                .resultMessage("Interests retrieved successfully")
                .data(interestsEntities)
                .build();

        return ResponseEntity.ok(response);
    }
}
