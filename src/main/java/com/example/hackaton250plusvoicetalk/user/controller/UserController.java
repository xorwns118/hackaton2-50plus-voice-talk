package com.example.hackaton250plusvoicetalk.user.controller;

import com.example.hackaton250plusvoicetalk.user.service.UserService;
import com.example.hackaton250plusvoicetalk.user.web.model.User;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type User controller.
 */
@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    /**
     * Join user response entity.
     *
     * @param userRequest the user request
     * @return the response entity
     */
    @PostMapping("/join")
    public ResponseEntity<User> joinUser(@RequestBody UserRequest userRequest) {
        var result = userService.join(userRequest.getUsername(),
                userRequest.getPassword());

        return ResponseEntity.ok(result);
    }
}
