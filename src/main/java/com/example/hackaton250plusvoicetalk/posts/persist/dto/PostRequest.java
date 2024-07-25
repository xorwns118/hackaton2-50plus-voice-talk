package com.example.hackaton250plusvoicetalk.posts.persist.dto;

import com.example.hackaton250plusvoicetalk.posts.persist.entity.PostEntity;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {

    private String title;
    private String content;

    public PostEntity toEntity(UserEntity user) {
        return PostEntity.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
