package com.workintech.twitter_clone.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users", schema = "twitter_app")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Column(unique = true)//essız ısımler ıcın
    private String userName;


    @Email(message = "Geçerli e-posta giriniz")
    @Column(unique = true)//essız emaıller ıcın
    private String email;


    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6)
    private String password;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Tweet> tweets;
}
