package com.example.instagram.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 500)
    private String content;

    // 본인(comment)와 변수와의 관계
    //          N:1
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @Builder
    public Comment(String content,User user,Post post){
        this.content=content;
        this.user=user;
        this.post=post;
    }

}


