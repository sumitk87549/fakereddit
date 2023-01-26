package com.fakereddit.demo.repository;

import com.fakereddit.demo.dto.CommentsDto;
import com.fakereddit.demo.model.Comment;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByUser(User user);

    List<Comment> findAllByPost(Post post);
}
