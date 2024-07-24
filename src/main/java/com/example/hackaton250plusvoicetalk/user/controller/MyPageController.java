package com.example.hackaton250plusvoicetalk.user.controller;

import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import com.example.hackaton250plusvoicetalk.user.service.UserService;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * The type My page controller.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SessionAttributes("users")
public class MyPageController {
    private final UserService userService;

    /**
     * Gets user.
     *
     * @param userId             the user id who knows user details
     * @param httpServletRequest the http servlet request
     * @return the user info
     */
    @CrossOrigin(origins = "http://10.230.110.50:3000")
    @GetMapping("/users/{user-id}")
    public Api<UserEntity> getUser(@PathVariable("user-id") Long userId, HttpServletRequest httpServletRequest){
        return userService.getUser(userId, httpServletRequest);
    }

    /**
     * Edit user api.
     *
     * @param userId             the user id who wants to edit user details
     * @param userRequest        the user request
     * @param httpServletRequest the http servlet request
     * @return the edited user info
     */
    @CrossOrigin(origins = "http://10.230.110.50:3000")
    @PatchMapping("/users/{user-id}")
    public Api<UserEntity> editUser(@PathVariable("user-id") Long userId, @RequestBody @Valid Api<UserRequest> userRequest,
                                    HttpServletRequest httpServletRequest){
        return userService.updateUser(userId, userRequest, httpServletRequest, true);
    }

/*    @PatchMapping("/users/{user-id}/password")
    public Api<UserEntity> editUserPassword(@PathVariable("user-id") Long userId, @RequestBody @Valid Api<UserRequest> userRequest,
                                            HttpServletRequest httpServletRequest){
        return userService.updateUser(userId, userRequest, httpServletRequest, true);
    }*/


}
