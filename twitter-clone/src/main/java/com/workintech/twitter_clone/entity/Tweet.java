package com.workintech.twitter_clone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of= "id")
@Entity
@Table(name = "tweets", schema = "twitter_app")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200, message = "Tweet en fazla 200 karakter olabilir")
    @NotNull
    @NotBlank(message = "Tweet içeriği boş olamaz")
    private String content;




    private LocalDateTime createAt;

    @PrePersist
    protected void onCreate(){
        createAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setTweet(this);
    }

    @ManyToMany
    @JoinTable(
            name = "tweet_likes",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likes = new HashSet<>();
}
