package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3200")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/like/{tweetId}")
    public void likeTweet(@PathVariable Long tweetId){
        likeService.like(tweetId);
    }

    @PostMapping("/dislike/{tweetId}")
    public void dislikeTweet(@PathVariable Long tweetId){
        likeService.dislike(tweetId);
    }
}
