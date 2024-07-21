package com.example.hackaton250plusvoicetalk.user.controller;

import com.example.hackaton250plusvoicetalk.user.service.UserService;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import com.example.hackaton250plusvoicetalk.user.web.model.User;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // ========== GET ==========
    /**
     * Login string.
     *
     * @return login html
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * Join string.
     *
     * @return join html
     */
    @GetMapping("/join")
    public String join(){
        return "join";
    }


//    // ========== POST ==========
//    @PostMapping("/login")  // login->login.html logout->logout.html
//    public Api<UserRequest> login(@RequestBody ){
//
//    }

    /**
     * Join user response entity.
     *
     * @param userRequest 유저로부터 받은 정보
     * @return DB에 저장된 유저 정보
     */
    @PostMapping("/join")
    public Api<UserRequest> joinUser(@RequestBody @Valid Api<UserRequest> userRequest) {
        Api<UserRequest> response = userService.join(userRequest);
        return response;
    }



}
