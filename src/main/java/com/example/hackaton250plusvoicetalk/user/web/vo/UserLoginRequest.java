package com.example.hackaton250plusvoicetalk.user.web.vo;

import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.validation.annotation.MobileNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserLoginRequest {

    @MobileNumber
    @NotBlank
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @NotBlank
    private String password;


    private Authority authority;

    @JsonProperty("user_id") //user_id가 있어야 세션 바탕 회원 정보 가져오기가 가능
    private Long userId;

}
