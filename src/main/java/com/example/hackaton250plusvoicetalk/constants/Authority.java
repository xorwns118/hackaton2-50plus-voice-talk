package com.example.hackaton250plusvoicetalk.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Authority {
    ROLE_ADMIN("ADMIN"), ROLE_USER("USER");

    private String authorityName;

}
