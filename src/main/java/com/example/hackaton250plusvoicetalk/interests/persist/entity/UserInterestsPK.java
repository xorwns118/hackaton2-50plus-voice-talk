package com.example.hackaton250plusvoicetalk.interests.persist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
public class UserInterestsPK implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "interest_id")
    private Long interestId;

    public UserInterestsPK(Long userId, Long interestId){
        this.userId = userId;
        this.interestId = interestId;
    }
}
