package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.request.CommentRequestDto;
import com.workintech.twitter_clone.dto.response.CommentResponseDto;
import com.workintech.twitter_clone.entity.Comment;
import com.workintech.twitter_clone.entity.Tweet;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.exception.TwitterException;
import com.workintech.twitter_clone.repository.CommentRepository;
import com.workintech.twitter_clone.repository.TweetRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public CommentResponseDto create(CommentRequestDto commentRequestDto) {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Tweet tweet = tweetRepository.findById(commentRequestDto.tweetId())
                .orElseThrow(() -> new RuntimeException("Tweet bulunamadı: " + commentRequestDto.tweetId()));

        Comment comment = new Comment();
        comment.setContent(commentRequestDto.content());

        user.addComment(comment);
        tweet.addComment(comment);

        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment.getId(), savedComment.getContent(), user.getUsername(), savedComment.getCreateAt());
    }

    @Transactional
    @Override
    public CommentResponseDto update(Long id, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı, id: " + id));

        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!comment.getUser().getUsername().equals(loggedInUsername)) {
            throw new RuntimeException("Bu yorumu düzenleme yetkiniz yok!");
        }

        comment.setContent(commentRequestDto.content());
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getUsername(),
                savedComment.getCreateAt()
        );
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();

        String tweetOwner = comment.getTweet().getUser().getUsername();
        String commentOwner = comment.getUser().getUsername();

        if (loggedInUser.equals(tweetOwner) || loggedInUser.equals(commentOwner)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new TwitterException("Bu yorumu silme yetkiniz yok!", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public List<CommentResponseDto> findByTweetId(Long tweetId) {
        return commentRepository.findByTweetId(tweetId).stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getUsername(),
                        comment.getCreateAt()))
                .toList();
    }

    @Override
    public void deleteById(Long id) {

    }
}