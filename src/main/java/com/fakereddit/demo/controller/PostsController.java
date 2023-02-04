package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.PostRequestDto;
import com.fakereddit.demo.dto.PostResponseDto;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/posts/")
@SecurityRequirement(name = "JWT Bearer Authentication")
public class PostsController {
    private final PostsService postsService;

    @GetMapping()
    public ResponseEntity<List<PostResponseDto>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(postsService.save(postRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getPost(id));
    }

    @GetMapping("/by_subreddit/{id}")
    public ResponseEntity<List<PostResponseDto>> getPostsBySubreddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getPostsBySubreddit(id));
    }

    @GetMapping("/by_username/{name}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable String name){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getPostsByUsername(name));
    }
}
