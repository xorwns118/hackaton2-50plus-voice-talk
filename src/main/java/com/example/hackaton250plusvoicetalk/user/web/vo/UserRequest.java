package com.example.hackaton250plusvoicetalk.user.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserRequest {
    private String username;
    private String password;
    private String mobileNumber;
    private String birthDate;
    private String province;
    private String city;
}
