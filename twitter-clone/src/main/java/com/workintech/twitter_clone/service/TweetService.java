package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_clone.dto.request.TweetRequestDto;
import com.workintech.twitter_clone.dto.response.TweetResponseDto;
import com.workintech.twitter_clone.entity.Tweet;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAll();
    TweetResponseDto findById(Long id);
    TweetResponseDto create(TweetRequestDto tweetRequestDto);
    TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto);
    TweetResponseDto replaceOrCreate(Long id, TweetRequestDto tweetRequestDto);
    void deleteById(Long id);
    List<TweetResponseDto> findByUserId(Long userId);
}
