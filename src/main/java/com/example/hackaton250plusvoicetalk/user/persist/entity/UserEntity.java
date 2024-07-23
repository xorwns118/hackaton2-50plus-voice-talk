package com.example.hackaton250plusvoicetalk.user.persist.entity;


import com.example.hackaton250plusvoicetalk.constants.Authority;
import com.example.hackaton250plusvoicetalk.constants.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@ToString
@Builder
@Data
@DynamicInsert
@DynamicUpdate
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    private String username;
    private String password;

    @Column(name="mobile_number")
    private String mobileNumber;

    @Column(name="birth_date")
    private String birthDate;

    @Enumerated(EnumType.ORDINAL)   // 0 : man, 1 : woman
    private Gender gender;

    @Enumerated(EnumType.ORDINAL)   // 0 : admin, 1 : user
    private Authority authority;

    private String province;
    private String city;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(String.valueOf(this.authority)));
    }
}
