package com.fakereddit.demo.service;


import com.fakereddit.demo.dto.VoteDto;
import com.fakereddit.demo.exceptions.PostNotFoundException;
import com.fakereddit.demo.exceptions.SpringRedditException;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.Vote;
import com.fakereddit.demo.repository.PostRepository;
import com.fakereddit.demo.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fakereddit.demo.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void registerVote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(()->new PostNotFoundException("No post found for following vote: "+voteDto));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,authService.getCurrentUser());
        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new SpringRedditException("Already voted for this post...: "+ voteDto);
        }

        if (post.getVoteCount() == null){
            post.setVoteCount(0);
        } //REMOVE THIS CONDITIONAL BLOCK LATER BY CHECKING WHY VOTE COUNT IS REGISTERED AS NULL IN DATABASE WHEN 0.

        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount()+1);
        } else {
            post.setVoteCount(post.getVoteCount()-1);
        }
        postRepository.save(post);
        voteRepository.save(mapToVote(voteDto,post));

    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
