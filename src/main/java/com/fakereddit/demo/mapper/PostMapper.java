package com.fakereddit.demo.mapper;


import com.fakereddit.demo.dto.PostRequest;
import com.fakereddit.demo.dto.PostResponse;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.Subreddit;
import com.fakereddit.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user",target = "user")
    @Mapping(source = "subreddit",target = "subreddit")
    @Mapping(expression = "java(java.time.Instant.now())",target = "createdDate")
    @Mapping(source = "postRequest.description",target = "description")
    Post mapDtoToModel(PostRequest postRequest, User user, Subreddit subreddit);

    @Mapping(source = "postId",target = "id")
    @Mapping(source = "postName",target = "postName")
    @Mapping(source = "url",target = "url")
    @Mapping(source = "description",target = "description")
    @Mapping(source = "subreddit.name",target = "subredditName")
    @Mapping(source = "user.username",target = "username")
    PostResponse mapModelToDto(Post post);

//
//    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
////    @Mapping(target = "description",source = "postRequest.description")
//    @Mapping(target = "description",source = "postRequest.description") //CHECK LATER MIGHT GIVE FUNCTIONAL ERROR
//    @Mapping(target = "subreddit", source = "subreddit")
//    @Mapping(target = "user", source = "user")
//    Post map(PostRequest postRequest, Subreddit subreddit, User user);
//
//    @Mapping(target = "id",source = "postId")
//    @Mapping(target = "postName",source = "postName")
//    @Mapping(target = "description",source = "description")
//    @Mapping(target = "url",source = "url")
//    @Mapping(target = "subredditName",source = "subreddit.name")
//    @Mapping(target = "username",source = "user.username")
//    PostResponse map(Post post);



}
