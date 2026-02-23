package com.workintech.twitter_clone.repository;

import com.workintech.twitter_clone.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("SELECT t FROM Tweet t WHERE t.user.id = :userId")
    List<Tweet> findByUserId(@Param("userId") Long userId);
}
