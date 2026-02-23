package com.workintech.twitter_clone.util.mapper;

import com.workintech.twitter_clone.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_clone.dto.request.TweetRequestDto;
import com.workintech.twitter_clone.dto.response.CommentResponseDto;
import com.workintech.twitter_clone.dto.response.TweetResponseDto;
import com.workintech.twitter_clone.entity.Tweet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TweetMapper {

    public TweetResponseDto toResponseDto(Tweet tweet) {
        List<CommentResponseDto> commentDtos = tweet.getComments() != null
                ? tweet.getComments().stream().map(comment -> new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser() != null ? comment.getUser().getUsername() : "Anonim",
                comment.getCreateAt()
        )).toList()
                : new ArrayList<>();

        return new TweetResponseDto(
                tweet.getId(),
                tweet.getContent(),
                tweet.getUser() != null ? tweet.getUser().getUsername() : "Bilinmeyen Kullanıcı",
                tweet.getCreateAt(),
                commentDtos,
                0,
                false,tweet.getUser().getId()
        );
    }
    public Tweet toEntity(TweetRequestDto tweetRequestDto) {
        Tweet tweet = new Tweet();
        tweet.setContent(tweetRequestDto.content());
        return tweet;
    }

    public void updateEntity(Tweet tweetToUpdate, TweetPatchRequestDto tweetPatchRequestDto) {
        if (tweetPatchRequestDto != null && tweetPatchRequestDto.content() != null) {
            tweetToUpdate.setContent(tweetPatchRequestDto.content());
        }
    }
}