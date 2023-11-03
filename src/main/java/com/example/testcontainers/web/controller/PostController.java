package com.example.testcontainers.web.controller;

import com.example.testcontainers.model.Post;
import com.example.testcontainers.service.PostService;
import com.example.testcontainers.web.dto.PostDto;
import com.example.testcontainers.web.mapper.PostMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@Valid @RequestBody PostDto dto) {
        Post post = postMapper.toEntity(dto);
        post = postService.create(post);
        return postMapper.toDto(post);
    }

    @GetMapping
    public List<PostDto> getAll() {
        List<Post> posts = postService.getAll();
        return postMapper.toDto(posts);
    }

    @GetMapping("/{id}")
    public PostDto getById(@PathVariable long id) {
        Post post = postService.getById(id);
        return postMapper.toDto(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        postService.delete(id);
    }

}
