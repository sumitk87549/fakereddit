package com.fakereddit.demo.mapper;

import com.fakereddit.demo.dto.SubredditDto;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "noOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapModelSubreddittoSubredditDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> noOfPosts) { return  noOfPosts.size();}

    @InheritInverseConfiguration
    @Mapping(target = "posts",ignore = true)
    Subreddit mapSubredditDtoToModelSubreddit(SubredditDto subredditDto);
}
