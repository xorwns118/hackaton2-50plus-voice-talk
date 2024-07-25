package com.example.hackaton250plusvoicetalk.posts.controller;

import com.example.hackaton250plusvoicetalk.constants.SessionConst;
import com.example.hackaton250plusvoicetalk.posts.persist.dto.PostRequest;
import com.example.hackaton250plusvoicetalk.posts.persist.dto.PostResponse;
import com.example.hackaton250plusvoicetalk.posts.service.PostService;
import com.example.hackaton250plusvoicetalk.user.web.model.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.hackaton250plusvoicetalk.user.web.model.Api.buildApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public Api<List<PostResponse>> findByUserId(@RequestParam(value = "user_id") Long userId) {
        return Api.buildApiResponse(HttpStatus.OK, postService.findByUserId(userId));
    }

    @GetMapping("/list")
    public Api<List<PostResponse>> list() {
        return Api.buildApiResponse(HttpStatus.OK,postService.findAll());
    }

    @PostMapping("")
    public Api<PostResponse> create(@RequestBody Api<PostRequest> postDto, HttpServletRequest request) {
        Long userId = authenticated(request);
        if(userId==null) {
            return Api.buildApiResponse(HttpStatus.BAD_REQUEST,null);
        }
        return Api.buildApiResponse(HttpStatus.OK, postService.save(postDto.getData(),userId));
    }

    @PatchMapping("/{postId}")
    public Api<PostResponse> delete(@PathVariable Long postId, HttpServletRequest request) {
        Long userId = authenticated(request);

        if (userId == null) {
            return buildApiResponse(HttpStatus.BAD_REQUEST, null);
        }

        Optional<PostResponse> target = postService.delete(postId, userId);
        return target.map(postResponse -> buildApiResponse(HttpStatus.OK, postResponse))
                .orElseGet(() -> buildApiResponse(HttpStatus.BAD_REQUEST, null));
    }

    @PutMapping("/{postId}")
    public Api<PostResponse> update(
            @PathVariable Long postId,
            @RequestBody Api<PostRequest> postDto,
            HttpServletRequest request
    ) {
        Long userId = authenticated(request);

        if (userId == null) {
            return buildApiResponse(HttpStatus.BAD_REQUEST, null);
        }

        Optional<PostResponse> target = postService.update(postDto.getData(), postId, userId);
        return target.map(postResponse -> buildApiResponse(HttpStatus.OK, postResponse))
                .orElseGet(() -> buildApiResponse(HttpStatus.BAD_REQUEST, null));
    }

    private Long authenticated(HttpServletRequest request) {
        if(request.getSession()==null || request.getSession().getAttribute(SessionConst.LOGIN_USER)==null) {
            return null;
        } else {
            return (Long)request.getSession().getAttribute(SessionConst.LOGIN_USER);
        }
    }
}
