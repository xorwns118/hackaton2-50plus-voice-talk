package com.example.hackaton250plusvoicetalk.user.controller;

import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.user.service.UserService;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserLoginRequest;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 */
@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SessionAttributes("users")
public class UserController {
    private final UserService userService;

    // ========== GET ==========

    /**
     * Index string.
     *
     * @return main page
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Logout string.
     *
     * @param httpServletRequest get httpServletRequest for session
     * @return main page
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        if(session != null){
            session.invalidate();
        }
        return "index";
    }


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

// ========== POST ==========

    /**
     * Login user api.
     *
     * @param userLoginRequest   the user login request
     * @param httpServletRequest the http servlet request
     * @return the api
     */
//    @CrossOrigin(origins = "http://10.230.120.40:3000")
    @CrossOrigin(origins = "http://192.168.219.102:3000")
    @PostMapping("/logins")  // login->login.html logout->logout.html
    public Api<UserLoginRequest> loginUser(@RequestBody @Valid Api<UserLoginRequest> userLoginRequest, HttpServletRequest httpServletRequest){
        try{
            // check Authority by using mobile number
            if(userLoginRequest.getData().getMobileNumber().equals("010-0000-0000")){
                userLoginRequest.getData().setAuthority(Authority.ROLE_ADMIN);
            }else{
                userLoginRequest.getData().setAuthority(Authority.ROLE_USER);
            }

            Api<UserLoginRequest> response = userService.login(userLoginRequest, httpServletRequest);
            return response;
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());

            return Api.<UserLoginRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.NOT_FOUND))
                    .resultMessage(e.getMessage())
                    .build();
        }
    }

    /**
     * Join user response entity.
     *
     * @param userRequest information got from user
     * @return user info stored at DB
     */
    @CrossOrigin(origins = "http://192.168.219.102:3000")
    @PostMapping("/join")
    public Api<UserRequest> joinUser(@RequestBody @Valid Api<UserRequest> userRequest) {
        return userService.join(userRequest);
    }







}
