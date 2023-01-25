package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.SubredditDto;
import com.fakereddit.demo.model.Subreddit;
import com.fakereddit.demo.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;
    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAllSubreddits());
    }

    @GetMapping("{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
    }
}
