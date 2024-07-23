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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public Api<UserRequest> join(@Valid Api<UserRequest> userRequest) {     // TODO: 중복 데이터 체크 필요
        var body = userRequest.getData();

        String encryptedPassword = bCryptPasswordEncoder.encode(body.getPassword());
        body.setPassword(encryptedPassword);

        // check if userRequest is admin
        if(body.getMobileNumber().equals("010-0000-0000")){ // set authority as admin
            body.setAuthority(Authority.ROLE_ADMIN);
        }else{
            body.setAuthority(Authority.ROLE_USER);
        }

        Api<UserRequest> response;
        // check if userRequest's mobileNumber already exists in DB
        if(getUserDetailsByMobileNumber(body.getMobileNumber()).isPresent()){ // user already exists
            response = Api.<UserRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.ALREADY_REPORTED.value()))
                    .resultMessage(HttpStatus.ALREADY_REPORTED.getReasonPhrase())
                    .data(null)
                    .build();
        }else{
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

            response = Api.<UserRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.OK.value()))
                    .resultMessage(HttpStatus.OK.getReasonPhrase())
                    .data(body)
                    .build();

        }


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

        UserEntity userInfoEntity = getUserDetailsByMobileNumber(loginMobileNumber).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Api<UserLoginRequest> response;
        HttpSession session = httpServletRequest.getSession();
        Object loginUserId = session.getAttribute(SessionConst.LOGIN_USER);
        Object loginAdminId = session.getAttribute(SessionConst.LOGIN_ADMIN);


        if(loginUserId != null && loginUserId.equals(userInfoEntity.getUserId())){  // session doesn't have logined user
            return Api.<UserLoginRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.IM_USED.value()))
                    .resultMessage(HttpStatus.IM_USED.getReasonPhrase())
                    .data(body)
                    .build();
        }else if(loginAdminId != null && loginAdminId.equals(userInfoEntity.getUserId())){
            return Api.<UserLoginRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.IM_USED.value()))
                    .resultMessage(HttpStatus.IM_USED.getReasonPhrase())
                    .data(body)
                    .build();
        }

        if(userInfoEntity != null && bCryptPasswordEncoder.matches(loginPassword, userInfoEntity.getPassword())){   // login success
            // TODO: Authority에 따른 리다이렉트 페이지 다르게 연결
            if(body.getAuthority().equals(Authority.ROLE_USER)){
                session.setAttribute(SessionConst.LOGIN_USER, userInfoEntity.getUserId());
            }else{
                session.setAttribute(SessionConst.LOGIN_ADMIN, userInfoEntity.getUserId());
            }
            body.setUserId(userInfoEntity.getUserId()); //user_id 리턴되도록 수정 [fix]
            response = Api.<UserLoginRequest>builder()
                    .resultCode(String.valueOf(HttpStatus.OK.value()))
                    .resultMessage(HttpStatus.OK.getReasonPhrase())
                    .data(body)
                    .build();
        }else{                                                                                                      // login failure
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
     * Get user api.
     *
     * @param userId             the user id who want to get user info
     * @param httpServletRequest the http servlet request
     * @return the user info about user id
     */
    public Api<UserEntity> getUser(Long userId, HttpServletRequest httpServletRequest){
        UserEntity userInfoEntity = getUserDetailsByUserId(userId);
        Api<UserEntity> response;
        HttpSession httpSession = httpServletRequest.getSession();

        if(httpSession.getAttribute(SessionConst.LOGIN_USER).equals(userId)){   // TODO: LOGIN_ADMIN 처리 안됨
            response = Api.<UserEntity>builder()
                    .resultCode(String.valueOf(HttpStatus.OK.value()))
                    .resultMessage(HttpStatus.OK.getReasonPhrase())
                    .data(userInfoEntity)
                    .build();
        }else{
            log.info("not found user");
            response = Api.<UserEntity>builder()
                    .resultMessage(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .resultMessage(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .data(null)
                    .build();
        }

        return response;
    }

    /**
     * Update user api.
     *
     * @param userId             the user id who wants to update user info
     * @param userRequest        the user request for update
     * @param httpServletRequest the http servlet request
     * @param isPassword         the is password if only want to change password
     * @return edited user info and result code, message
     */
    public Api<UserEntity> updateUser(Long userId, Api<UserRequest> userRequest, HttpServletRequest httpServletRequest, Boolean isPassword){
        Api<UserEntity> response;
        var body = userRequest.getData();
        HttpSession httpSession = httpServletRequest.getSession();
        String encodedPassword;

        if(isPassword){
            // Update password
            encodedPassword = bCryptPasswordEncoder.encode(userRequest.getData().getPassword());
            userRequest.getData().setPassword(encodedPassword);
        }else{
            encodedPassword = bCryptPasswordEncoder.encode(body.getPassword());
        }

        if(httpSession.getAttribute(SessionConst.LOGIN_USER).equals(userId)){   // TODO: LOGIN_ADMIN 처리 안됨

            UserEntity userEntity = UserEntity.builder()
                    .userId(userId)
                    .username(body.getUsername())
                    .password(encodedPassword)
                    .mobileNumber(body.getMobileNumber())
                    .birthDate(body.getBirthDate())
                    .gender(body.getGender())
                    .authority(Authority.ROLE_USER)
                    .province(body.getProvince())
                    .city(body.getCity())
                    .build();

            userRepository.save(userEntity);



            response = Api.<UserEntity>builder()
                    .resultCode(String.valueOf(HttpStatus.OK.value()))
                    .resultMessage(HttpStatus.OK.getReasonPhrase())
                    .data(userEntity)
                    .build();
        }else{
            log.info("not found user");
            response = Api.<UserEntity>builder()
                    .resultMessage(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .resultMessage(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .data(null)
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
    public Optional<UserEntity> getUserDetailsByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
               /* .orElseThrow(() -> new IllegalArgumentException("User not found with mobile number: " + mobileNumber));*/
    }

    /**
     * Get user details by user id user entity.
     *
     * @param userId the user id
     * @return the user entity
     */
    public UserEntity getUserDetailsByUserId(Long userId){
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with user id: " + userId));
    }

    /**
     * To entity user entity.
     *
     * @param request the request
     * @param userId  the user id
     * @return the user entity
     */
    public UserEntity toEntity(UserRequest request, Long userId) {
        return UserEntity.builder()
                .userId(userId)
                .username(request.getUsername())
                .password(request.getPassword())
                .mobileNumber(request.getMobileNumber())
                .birthDate(request.getBirthDate())
                .gender(request.getGender())
                .authority(request.getAuthority())
                .province(request.getProvince())
                .city(request.getCity())
                .build();
    }

}
