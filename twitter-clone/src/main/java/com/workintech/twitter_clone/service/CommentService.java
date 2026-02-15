package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.request.CommentRequestDto;
import com.workintech.twitter_clone.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto create(CommentRequestDto commentRequestDto);

    List<CommentResponseDto> findByTweetId(Long tweetId);

    void deleteById(Long id);

    void deleteComment(Long commentId);

    CommentResponseDto update(Long id, CommentRequestDto commentRequestDto);
}
