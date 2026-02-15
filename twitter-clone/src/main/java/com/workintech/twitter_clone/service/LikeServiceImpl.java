package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService{

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void like(Long tweetId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow();

        tweet.getLikes().add(user);
        tweetRepository.save(tweet);
    }

    @Transactional
    public void dislike(Long tweetId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow();

        tweet.getLikes().remove(user);
        tweetRepository.save(tweet);
    }
}
