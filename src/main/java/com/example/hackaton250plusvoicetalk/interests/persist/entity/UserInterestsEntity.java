package com.example.hackaton250plusvoicetalk.interests.persist.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@ToString
@Builder
@Entity(name = "User_Interests")
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class UserInterestsEntity {
    @Id
    @EmbeddedId
    private UserInterestsPK userInterestsPK;
}
