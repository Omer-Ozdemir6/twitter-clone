package com.workintech.twitter_clone.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of="id")
@Entity
@Table(name = "users", schema = "twitter_app")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Size(max = 50)
    @Column(name = "user_name", unique = true)//essız ısımler ıcın
    private String username;


    @NotNull
    @Size(max = 100)
    @Column(unique = true)//essız emaıller ıcın
    @Email(message = "Geçerli bir email adresi giriniz")
    private String email;

    @NotNull
    @Size(min = 6)
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Tweet> tweets = new HashSet<>();

    public void addTweet(Tweet tweet){
        this.tweets.add(tweet);
        tweet.setUser(this);
    }

    public void removeTweet(Tweet tweet){
        this.tweets.remove(tweet);
        tweet.setUser(null);
    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", schema = "twitter_app",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setUser(this);
    }
}


