package com.example.hackaton250plusvoicetalk.interests.controller;

import com.example.hackaton250plusvoicetalk.interests.persist.entity.UserInterestsEntity;
import com.example.hackaton250plusvoicetalk.interests.service.UserInterestsService;
import com.example.hackaton250plusvoicetalk.interests.web.vo.UpdateInterestsRequest;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SessionAttributes("users") /// api/users/{id}/interests
public class UserInterestsController {
    private final UserInterestsService userInterestsService;

    @PostMapping("/users/{id}/interests")
    public ResponseEntity<List<UserInterestsEntity>> addInterest(@PathVariable Long id, @RequestBody UpdateInterestsRequest updateInterestRequest) {

        List<UserInterestsEntity> userInterestsEntities = updateInterestRequest.getInterestIds().stream()
                .map(interestId -> userInterestsService.addInterest(id, interestId))
                .toList();

        System.out.println(userInterestsEntities);

        return ResponseEntity.ok(userInterestsEntities);
    }

    @DeleteMapping("/users/{id}/interests")
    public ResponseEntity<Api<Void>> removeInterest(@PathVariable Long id, @RequestBody UpdateInterestsRequest updateInterestRequest) {
        updateInterestRequest.getInterestIds().forEach(
                interestId -> userInterestsService.removeInterest(id, interestId)
        );

        Api<Void> response = Api.<Void>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .build();

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/users/{id}/interests")
    public ResponseEntity<Api<List<UserInterestsEntity>>> getUserInterests(@PathVariable Long id){
        List<UserInterestsEntity> userInterestsEntities = userInterestsService.getUserInterests(id);

        Api<List<UserInterestsEntity>> response = Api.<List<UserInterestsEntity>>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(userInterestsEntities)
                .build();

        return ResponseEntity.ok(response);
    }
}
