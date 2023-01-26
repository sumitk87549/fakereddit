package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.PostRequest;
import com.fakereddit.demo.dto.PostResponse;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.service.PostsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/posts/")
public class PostsController {
    private final PostsService postsService;

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(postsService.save(postRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getPost(id));
    }

    @GetMapping("/by_subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getPostsBySubreddit(id));
    }

    @GetMapping("/by_username/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(postsService.getPostsByUsername(username));
    }
}
