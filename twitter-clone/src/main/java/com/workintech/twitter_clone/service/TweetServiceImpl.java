package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_clone.dto.request.TweetRequestDto;
import com.workintech.twitter_clone.dto.response.TweetResponseDto;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exception.TwitterException;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import com.workintech.twitter_clone.util.mapper.TweetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TweetMapper tweetMapper;


    @Override
    public List<TweetResponseDto> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return tweetRepository.findAll().stream().map(tweet -> {

            TweetResponseDto tempDto = tweetMapper.toResponseDto(tweet);

            boolean isLiked = tweet.getLikes().stream()
                    .anyMatch(user -> user.getUsername().equals(username));

            return new TweetResponseDto(
                    tempDto.id(),
                    tempDto.content(),
                    tempDto.username(),
                    tempDto.createdAt(),
                    tempDto.comments(),
                    tweet.getLikes().size(),
                    isLiked,
                    tweet.getUser().getId()
            );
        }).toList();
    }

    @Override
    public TweetResponseDto findById(Long id) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new RuntimeException("Tweet bulunamadı, id: " + id));
        return tweetMapper.toResponseDto(tweet);
    }


    @Override
    public TweetResponseDto create(TweetRequestDto tweetRequestDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new TwitterException("Giriş yapan kullanıcı bulunamadı", HttpStatus.NOT_FOUND));

        Tweet tweet = tweetMapper.toEntity(tweetRequestDto);
        tweet.setUser(user);

        tweetRepository.save(tweet);
        return tweetMapper.toResponseDto(tweet);
    }

    @Transactional
    @Override
    public TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto) {
        Tweet existingTweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TwitterException("Tweet bulunamadı! ID: " + id, HttpStatus.NOT_FOUND));

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!existingTweet.getUser().getUsername().equals(loggedInUsername)) {
            throw new TwitterException("Sadece kendi tweetlerinizi güncelleyebilirsiniz!", HttpStatus.FORBIDDEN);
        }

        existingTweet.setContent(tweetPatchRequestDto.content());


        Tweet savedTweet = tweetRepository.save(existingTweet);


        return tweetMapper.toResponseDto(savedTweet);
    }


    @Override
    public TweetResponseDto replaceOrCreate(Long id, TweetRequestDto tweetRequestDto) {
        Tweet tweet = tweetMapper.toEntity(tweetRequestDto);

        User user = userRepository.findById(tweetRequestDto.userId()).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı"));
        tweet.setUser(user);

        Optional<Tweet> optionalTweet = tweetRepository.findById(id);
        if (optionalTweet.isPresent()){
            tweet.setId(id);
        }
        tweetRepository.save(tweet);
        return tweetMapper.toResponseDto(tweet);
    }

    @Override
    public void deleteById(Long id) {

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TwitterException("Tweet bulunamadı", HttpStatus.NOT_FOUND));

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!tweet.getUser().getUsername().equals(loggedInUsername)) {
            throw new TwitterException("Bu tweeti silmeye yetkiniz yok!", HttpStatus.FORBIDDEN);
        }

        tweetRepository.deleteById(id);
    }

    @Override
    public List<TweetResponseDto> findByUserId(Long userId) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        return tweetRepository.findByUserId(userId)
                .stream()
                .map(tweet -> {
                    TweetResponseDto temp = tweetMapper.toResponseDto(tweet);
                    boolean isLiked = tweet.getLikes().stream()
                            .anyMatch(u -> u.getUsername().equals(currentUsername));

                    return new TweetResponseDto(
                            temp.id(), temp.content(), temp.username(),
                            temp.createdAt(), temp.comments(),
                            tweet.getLikes().size(), isLiked,tweet.getUser().getId()
                    );
                }).toList();
    }
}
