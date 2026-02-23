package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.dto.request.CommentRequestDto;
import com.workintech.twitter_clone.dto.response.CommentResponseDto;
import com.workintech.twitter_clone.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "http://localhost:3200")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{id}")
    public CommentResponseDto create(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.create(id, commentRequestDto);
    }


    @PatchMapping("/{id}")
    public CommentResponseDto update(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.update(id, commentRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commentService.deleteById(id);
    }
}