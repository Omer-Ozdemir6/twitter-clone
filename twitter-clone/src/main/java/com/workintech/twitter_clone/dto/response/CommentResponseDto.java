package com.workintech.twitter_clone.dto.response;

import java.time.LocalDateTime;

public record CommentResponseDto(Long id, String content, String username, LocalDateTime createAt) {
}
