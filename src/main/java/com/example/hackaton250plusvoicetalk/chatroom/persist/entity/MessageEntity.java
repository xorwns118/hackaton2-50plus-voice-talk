package com.example.hackaton250plusvoicetalk.chatroom.persist.entity;

import com.example.hackaton250plusvoicetalk.chatroom.persist.entity.ChatEntity;
import com.example.hackaton250plusvoicetalk.common.BaseEntity;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@ToString
@Builder
@Entity(name = "messages")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatEntity chat;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


}
