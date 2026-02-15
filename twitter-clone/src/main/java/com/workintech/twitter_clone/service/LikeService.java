package com.workintech.twitter_clone.service;

public interface LikeService {

    void like(Long tweetId);
    void dislike(Long tweetId);
}
