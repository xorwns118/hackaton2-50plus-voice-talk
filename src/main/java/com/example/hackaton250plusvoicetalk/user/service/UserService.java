package com.example.hackaton250plusvoicetalk.user.service;


import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.constants.SessionConst;
import com.example.hackaton250plusvoicetalk.user.persist.UserRepository;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserLoginRequest;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Optional;

/**
 * The type User service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Join api.
     *
     * @param userRequest user join request
     * @return user join data and HttpStatus info
     */
    @Transactional
    public Api<UserRequest> join(@Valid Api<UserRequest> userRequest) {
        var body = userRequest.getData();

        String encryptedPassword = bCryptPasswordEncoder.encode(body.getPassword());
        body.setPassword(encryptedPassword);
        if(body.getMobileNumber().equals("010-0000-0000")){ // set authority as admin
            body.setAuthority(Authority.ROLE_ADMIN);
        }else{
            body.setAuthority(Authority.ROLE_USER);
        }

        UserEntity userEntity = UserEntity.builder()
                .username(body.getUsername())
                .password(body.getPassword())
                .mobileNumber(body.getMobileNumber())
                .birthDate(body.getBirthDate())
                .gender(body.getGender())
                .authority(Authority.ROLE_USER)
                .province(body.getProvince())
                .city(body.getCity())
                .build();

        userRepository.save(userEntity);

        Api<UserRequest> response = Api.<UserRequest>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(body)
                .build();
        return response;
    }

    /**
     * Login api.
     *
     * @param userLoginRequest   the user login request
     * @param httpServletRequest the http servlet request for login session
     * @return login data and HttpStatus info
     */
    public Api<UserLoginRequest> login(Api<UserLoginRequest> userLoginRequest, HttpServletRequest httpServletRequest) {
        var body = userLoginRequest.getData();

        String loginMobileNumber = body.getMobileNumber();
        String loginPassword = body.getPassword();

        UserEntity userInfoEntity = getUserDetailsByMobileNumber(loginMobileNumber);
        Api<UserLoginRequest> response;

        if(userInfoEntity != null && bCryptPasswordEncoder.matches(loginPassword, userInfoEntity.getPassword())){   // login success
            HttpSession session = httpServletRequest.getSession();
            // TODO: Authority에 따른 리다이렉트 페이지 다르게 연결
            if(body.getAuthority().equals(Authority.ROLE_USER)){
                session.setAttribute(SessionConst.LOGIN_USER, loginMobileNumber);
            }else{
                session.setAttribute(SessionConst.LOGIN_ADMIN, loginMobileNumber);
            }
            response = Api.<UserLoginRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.OK.value()))
                    .resultMessage(HttpStatus.OK.getReasonPhrase())
                    .data(body)
                    .build();
        }else{                                                                                  // login failure
            log.info("wrong password");
            response = Api.<UserLoginRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .resultMessage(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .data(body)
                    .build();
        }
        return response;
    }


    /**
     * Gets user details by mobile number.
     *
     * @param mobileNumber the mobile number
     * @return the user details found by mobile number
     */
    public UserEntity getUserDetailsByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found with mobile number: " + mobileNumber));
    }

}
