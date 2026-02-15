package com.workintech.twitter_clone.repository;

import com.workintech.twitter_clone.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByTweetId(Long tweetId);
}
