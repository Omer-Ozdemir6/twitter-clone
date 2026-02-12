package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.repository.TweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository){
        this.tweetRepository = tweetRepository;
    }

    public Tweet saveTweet(Tweet tweet){
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getTweerById(Long userId){
        return tweetRepository.findByUserId(userId);
    }

    public Tweet getTweetById(Long id){
        return tweetRepository.findById(id).orElseThrow(()-> new RuntimeException("Tweet bulunamadı!"));
    }

    public void delete(Long id, Long currentUserId){
        Tweet tweet = getTweetById(id);
        if (!tweet.getUser().getId().equals(currentUserId)){
            throw new RuntimeException("Bu tweeti silmeye yetkiniz yok!");
        }
        tweetRepository.deleteById(id);
    }
}
