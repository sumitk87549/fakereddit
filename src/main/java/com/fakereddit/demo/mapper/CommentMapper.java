package com.fakereddit.demo.mapper;

import com.fakereddit.demo.dto.CommentsDto;
import com.fakereddit.demo.model.Comment;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(ignore = true , target = "id")
    @Mapping(source = "commentsDto.text", target = "text")
    @Mapping(expression = "java(java.time.Instant.now())", target = "createdDate")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "post", target = "post")
    Comment mapDtoToModel(CommentsDto commentsDto, Post post, User user);

    @Mapping(expression = "java(comment.getPost().getPostId())", target = "postId")
    @Mapping(expression = "java(comment.getUser().getUsername())", target = "username")
    CommentsDto mapModelToDto(Comment comment);


}
