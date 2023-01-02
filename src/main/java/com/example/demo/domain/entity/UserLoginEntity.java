package com.example.demo.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_login")
@Getter
public class UserLoginEntity {
    @Id
    private Integer userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String token;

    private LocalDateTime expiresAt;
}
