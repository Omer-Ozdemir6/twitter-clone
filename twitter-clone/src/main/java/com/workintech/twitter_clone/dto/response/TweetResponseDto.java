package com.workintech.twitter_clone.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record TweetResponseDto(Long id, String content, String username, LocalDateTime createdAt, List<CommentResponseDto> comments, int likesCount,boolean likedByCurrentUser,Long userId) {
}
