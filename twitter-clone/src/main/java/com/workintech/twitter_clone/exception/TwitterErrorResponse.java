package com.workintech.twitter_clone.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwitterErrorResponse {

    private int status;
    private String message;
    private long timestamp;
    private LocalDateTime localDateTime;
}
