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

    @Mapping( expression = "java(mapPosts(subreddit.getPosts()))", target = "noOfPosts")
    SubredditDto mapModelToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> noOfPosts) { return  noOfPosts.size();}

    @InheritInverseConfiguration
    @Mapping(ignore = true, target = "posts")
    Subreddit mapDtoToModel(SubredditDto subredditDto);
}
