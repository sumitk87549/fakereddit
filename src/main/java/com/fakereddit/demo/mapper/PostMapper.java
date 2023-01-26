package com.fakereddit.demo.mapper;


import com.fakereddit.demo.dto.PostRequestDto;
import com.fakereddit.demo.dto.PostResponseDto;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.Subreddit;
import com.fakereddit.demo.model.User;
import com.fakereddit.demo.repository.CommentRepository;
import com.fakereddit.demo.repository.VoteRepository;
import com.fakereddit.demo.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(source = "user",target = "user")
    @Mapping(source = "subreddit",target = "subreddit")
    @Mapping(expression = "java(java.time.Instant.now())",target = "createdDate")
    @Mapping(source = "postRequestDto.description",target = "description")
    @Mapping(constant = "0",target = "voteCount")
    public abstract Post mapDtoToModel(PostRequestDto postRequestDto, User user, Subreddit subreddit);

    @Mapping(source = "postId",target = "id")
    @Mapping(source = "postName",target = "postName")
    @Mapping(source = "url",target = "url")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "subreddit.name",target = "subredditName")
    @Mapping(source = "user.username",target = "username")
    @Mapping(expression = "java(commentsCount(post))",target = "commentCount")
    @Mapping(expression = "java(getDuration(post))",target = "duration")
    @Mapping(expression = "java(getVoteCount(post.getVoteCount()))",target = "voteCount")
    public abstract PostResponseDto mapModelToDto(Post post);

    Integer commentsCount(Post post){
        return commentRepository.findAllByPost(post).size();

    }
    Integer getVoteCount(Integer voteCount){
        if(voteCount == null)
            return 0;
        else
            return voteCount;

    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

}
