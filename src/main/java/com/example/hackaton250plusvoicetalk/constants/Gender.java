package com.example.hackaton250plusvoicetalk.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Gender {
    GENDER_MAN("MAN"), GENDER_WOMAN("WOMAN");

    private String genderType;
}
