package com.example.hackaton250plusvoicetalk.posts.persist.entity;


import com.example.hackaton250plusvoicetalk.chatroom.persist.entity.ChatEntity;
import com.example.hackaton250plusvoicetalk.common.BaseEntity;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;


@Entity(name = "posts")
@Getter
@Setter
@ToString
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // dto에 not null 추가
    @Column(nullable = false)
    private String title;

    // dto에 not null 추가
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // dto -> entity 변환 시 default 넣어주기
    @Column(nullable = false)
    private Boolean chatStatus;

}

