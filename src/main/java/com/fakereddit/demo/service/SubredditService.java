package com.fakereddit.demo.service;

import com.fakereddit.demo.dto.SubredditDto;
import com.fakereddit.demo.exceptions.SubredditNotFoundException;
import com.fakereddit.demo.mapper.SubredditMapper;
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
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit newlySavedSubreddit = subredditRepository.save(subredditMapper.mapDtoToModel(subredditDto));
        subredditDto.setId(newlySavedSubreddit.getId());
        return subredditDto;
    }

    @Transactional
    public List<SubredditDto> getAllSubreddits(){
        return subredditRepository.findAll().stream()
                .map(subredditMapper::mapModelToDto)
                .collect(Collectors.toList());
    }

    public SubredditDto getSubreddit(Long id) {
        return subredditMapper.mapModelToDto(subredditRepository.findById(id).orElseThrow(() -> new SubredditNotFoundException("No subreddit found for id- "+id)));
    }
}
