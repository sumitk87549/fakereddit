package com.fakereddit.demo.service;

import com.fakereddit.demo.dto.SubredditDto;
import com.fakereddit.demo.model.Subreddit;
import com.fakereddit.demo.repository.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit newlySavedSubreddit = subredditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(newlySavedSubreddit.getId());
        return subredditDto;
    }

    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder()
                .name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();

    }

    @Transactional
    public List<SubredditDto> getAllSubreddits(){
        return subredditRepository.findAll().stream()
                .map(this::mapToSubredditDto)
                .collect(Collectors.toList());
    }

    private SubredditDto mapToSubredditDto(Subreddit subreddit) {
        return SubredditDto.builder()
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .build();
    }
}
