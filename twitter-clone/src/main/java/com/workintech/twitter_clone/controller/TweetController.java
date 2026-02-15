package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_clone.dto.request.TweetRequestDto;
import com.workintech.twitter_clone.dto.response.TweetResponseDto;
import com.workintech.twitter_clone.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tweet")
public class TweetController {

        @Autowired
    private TweetService tweetService;

        @GetMapping
    public List<TweetResponseDto> getAll(){
            return tweetService.getAll();
        }

        @GetMapping("/{id}")
    public TweetResponseDto findById(@PathVariable("id") Long id){
            return tweetService.findById(id);
        }

        @GetMapping("/user/{userId}")
    public List<TweetResponseDto> findByUserId(@PathVariable("userId") Long userId){
            return tweetService.findByUserId(userId);
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto create(@RequestBody TweetRequestDto tweetRequestDto){
            return tweetService.create(tweetRequestDto);
        }

        @PatchMapping("/{id}")
    public TweetResponseDto update(@PathVariable("id") Long id, @RequestBody TweetPatchRequestDto tweetPatchRequestDto){
            return tweetService.update(id, tweetPatchRequestDto);
        }

        @PutMapping("/{id}")
    public TweetResponseDto replaceOrCreate(@PathVariable("id") Long id, @RequestBody TweetRequestDto tweetRequestDto){
            return tweetService.replaceOrCreate(id, tweetRequestDto);
        }

        @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
            tweetService.deleteById(id);
        }



}
