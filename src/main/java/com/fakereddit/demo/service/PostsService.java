package com.fakereddit.demo.service;


import com.fakereddit.demo.dto.PostRequest;
import com.fakereddit.demo.dto.PostResponse;
import com.fakereddit.demo.exceptions.SpringRedditException;
import com.fakereddit.demo.exceptions.SubredditNotFoundException;
import com.fakereddit.demo.mapper.PostMapper;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.Subreddit;
import com.fakereddit.demo.model.User;
import com.fakereddit.demo.repository.PostRepository;
import com.fakereddit.demo.repository.SubredditRepository;
import com.fakereddit.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostsService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(()->new SubredditNotFoundException("Not subreddit found for post - "+postRequest));
        User currentUser = authService.getCurrentUser();
        return postRepository.save(postMapper.mapDtoToModel(postRequest,currentUser,subreddit));
    }

    public PostResponse getPost(Long id) {
        return postMapper.mapModelToDto(postRepository.findById(id).orElseThrow(()->new SpringRedditException("No Post found for id - "+id)));
    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::mapModelToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsBySubreddit(Long id) {
        return postRepository
                .findAllBySubreddit(
                        subredditRepository.findById(id).orElseThrow(()->new SubredditNotFoundException("No subreddit found for Id- "+id)))
                .stream()
                .map(postMapper::mapModelToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsByUsername(String username) {
        return postRepository
                .findByUser(
                        userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("No user found for username- " +username)))
                .stream()
                .map(postMapper::mapModelToDto)
                .collect(Collectors.toList());
    }
}
