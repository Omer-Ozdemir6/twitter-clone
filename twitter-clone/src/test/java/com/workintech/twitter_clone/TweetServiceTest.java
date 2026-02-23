package com.workintech.twitter_clone;

import com.workintech.twitter_clone.dto.request.TweetRequestDto;
import com.workintech.twitter_clone.dto.response.TweetResponseDto;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import com.workintech.twitter_clone.service.TweetServiceImpl;
import com.workintech.twitter_clone.util.mapper.TweetMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TweetServiceTest {

    @InjectMocks
    private TweetServiceImpl tweetService;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TweetMapper tweetMapper;

    @Test
    void shouldCreateTweetSuccessfully() {
        String username = "omer_ozdemir";
        TweetRequestDto requestDto = new TweetRequestDto("Test Tweet İçeriği", 1L);

        User user = new User();
        user.setUsername(username);

        Tweet tweet = new Tweet();
        tweet.setContent("Test Tweet İçeriği");
        tweet.setUser(user);

        TweetResponseDto responseDto = new TweetResponseDto(1L, "Test Tweet İçeriği", username, null, null, 0, false, 1L);

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(auth.getName()).thenReturn(username);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(tweetMapper.toEntity(any(TweetRequestDto.class))).thenReturn(tweet);
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
        when(tweetMapper.toResponseDto(any(Tweet.class))).thenReturn(responseDto);

        TweetResponseDto response = tweetService.create(requestDto);

        assertNotNull(response);
        assertEquals("Test Tweet İçeriği", response.content());
        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }
}