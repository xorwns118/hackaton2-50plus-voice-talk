package com.example.hackaton250plusvoicetalk.posts.service;

import com.example.hackaton250plusvoicetalk.posts.persist.PostRepository;
import com.example.hackaton250plusvoicetalk.posts.persist.dto.PostRequest;
import com.example.hackaton250plusvoicetalk.posts.persist.dto.PostResponse;
import com.example.hackaton250plusvoicetalk.posts.persist.entity.PostEntity;
import com.example.hackaton250plusvoicetalk.user.persist.UserRepository;
import com.example.hackaton250plusvoicetalk.user.persist.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponse save(PostRequest postDto, Long userId) {

        UserEntity user = userRepository.findByUserId(userId).get();
        PostEntity post = postRepository.save(postDto.toEntity(user));
        return PostResponse.toDto(post);
    }

    public Optional<PostResponse> update(PostRequest postDto, Long postId, Long userId) {
        Optional<PostEntity> target = postRepository.findById(postId);
        if(target.isPresent()) {
            PostEntity post = target.get();
            if(Objects.equals(post.getUser().getUserId(), userId)) {
                post.setTitle(postDto.getTitle());
                post.setContent(postDto.getContent());
                return Optional.of(PostResponse.toDto(postRepository.save(post)));
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Optional<PostResponse> delete(Long postId, Long userId) {
        PostEntity post = postRepository.findById(postId).get();
        if(!Objects.equals(post.getUser().getUserId(), userId)) {
            return Optional.empty();
        } else {
            postRepository.delete(post);
            return Optional.of(PostResponse.toDto(post));
        }
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll().stream()
                .map(PostResponse::toDto)
                .toList();
    }

    public List<PostResponse> findByUserId(Long userId) {
        return postRepository.findByUser_UserId(userId).stream()
                .map(PostResponse::toDto)
                .toList();
    }
}
