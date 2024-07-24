package com.example.hackaton250plusvoicetalk.chatroom.persist.entity;

import com.example.hackaton250plusvoicetalk.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;


@Getter
@Setter
@ToString
@Builder
@Entity(name = "chats")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chat")
    private List<ChatInfoEntity> chatInfo;

    @OneToMany(mappedBy = "chat")
    private List<MessageEntity> messages;

}
