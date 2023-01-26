package com.fakereddit.demo.controller;

import com.fakereddit.demo.dto.VoteDto;
import com.fakereddit.demo.model.Vote;
import com.fakereddit.demo.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> registerVote(@RequestBody VoteDto voteDto){
        voteService.registerVote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
