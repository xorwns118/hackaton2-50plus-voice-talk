package com.example.hackaton250plusvoicetalk.posts.persist.dto;

import com.example.hackaton250plusvoicetalk.posts.persist.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private String title;
    private String content;
    private String username;
    private Long userId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static PostResponse toDto(PostEntity post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .userId(post.getUser().getUserId())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
    }
}
