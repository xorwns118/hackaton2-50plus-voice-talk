package com.example.hackaton250plusvoicetalk.user.web.vo;

import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.constants.Gender;
import com.example.hackaton250plusvoicetalk.validation.annotation.MobileNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    @MobileNumber
    @NotBlank
    @JsonProperty("mobile_number")
    private String mobileNumber;

    @NotBlank
    @JsonProperty("birth_date")
    private String birthDate;


    private Authority authority;

    private Gender gender;
    @NotBlank
    private String province;
    @NotBlank
    private String city;
}
