package com.fakereddit.demo.repository;

import com.fakereddit.demo.dto.PostRequest;
import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.Subreddit;
import com.fakereddit.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);


//    PostRequest save(PostRequest postRequest);
}
