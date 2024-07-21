package com.example.hackaton250plusvoicetalk.user.service;


import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.user.persist.UserRepository;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import com.example.hackaton250plusvoicetalk.user.web.vo.UserRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserSecurityService userSecurityService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public Api<UserRequest> join(@Valid Api<UserRequest> userRequest) {
        var body = userRequest.getData();
        String encryptedPassword = bCryptPasswordEncoder.encode(body.getPassword());
        body.setPassword(encryptedPassword);
        body.setAuthority(Authority.ROLE_USER);

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

    public Api<UserRequest> login(Api<UserRequest> userRequest) {
        var body = userRequest.getData();
        UserDetails userDetails = userSecurityService.loadUserByMobileNumber(body.getMobileNumber());

        Api<UserRequest> response = Api.<UserRequest>builder()
                .resultCode(String.valueOf(HttpStatus.OK.value()))
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(body)
                .build();

        return response;
    }
}
