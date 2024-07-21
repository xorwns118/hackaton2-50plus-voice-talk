package com.example.hackaton250plusvoicetalk.user.web.model;

import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.constants.Gender;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
public class User {
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @Column(name="mobile_number")
    private String mobileNumber;
    @Column(name="birth_date")
    private String birthDate;
    private Gender gender;
    private Authority authority;
    private String province;
    private String city;
}
