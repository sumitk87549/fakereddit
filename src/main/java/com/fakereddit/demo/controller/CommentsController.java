package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.CommentsDto;
import com.fakereddit.demo.repository.CommentRepository;
import com.fakereddit.demo.service.CommentsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
@SecurityRequirement(name = "JWT Bearer Authentication")
public class CommentsController {
    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<CommentsDto> createComment(@RequestBody CommentsDto commentsDto){
        return ResponseEntity.status(CREATED).body(commentsService.createComment(commentsDto));
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId){
        return ResponseEntity.status(OK).body(commentsService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@PathVariable String username){
        return ResponseEntity.status(OK).body(commentsService.getAllCommentsForUser(username));
    }

}
