package com.fakereddit.demo.repository;

import com.fakereddit.demo.model.Post;
import com.fakereddit.demo.model.User;
import com.fakereddit.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
