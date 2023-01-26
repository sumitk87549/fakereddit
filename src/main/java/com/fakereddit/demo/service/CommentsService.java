package com.fakereddit.demo.service;


import com.fakereddit.demo.dto.CommentsDto;
import com.fakereddit.demo.exceptions.PostNotFoundException;
import com.fakereddit.demo.exceptions.SpringRedditException;
import com.fakereddit.demo.mapper.CommentMapper;
import com.fakereddit.demo.model.Comment;
import com.fakereddit.demo.model.NotificationEmail;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.User;
import com.fakereddit.demo.repository.CommentRepository;
import com.fakereddit.demo.repository.PostRepository;
import com.fakereddit.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentsService {
    private static final String POST_URL="";
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public CommentsDto createComment(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(()->new SpringRedditException("unable to get post for comment- "+commentsDto));
        Comment comment = commentRepository.save(commentMapper.mapDtoToModel(commentsDto,post, authService.getCurrentUser()));
        String message = mailContentBuilder.build(post.getUser().getUsername()+" commented on your post."+POST_URL);
        sendCommentNotification(message,post.getUser());
        return commentMapper.mapModelToDto(comment);
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendEmail(new NotificationEmail(user.getUsername() + " commented on your post.", user.getEmail(),message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        return commentRepository
                .findAllByPost(
                        postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException("No post found for Id- "+ postId)))
                .stream()
                .map(commentMapper::mapModelToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String username) {
        return commentRepository
                .findAllByUser(
                    userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found for username- "+ username)))
                .stream()
                .map(commentMapper::mapModelToDto)
                .collect(Collectors.toList());
    }
}
