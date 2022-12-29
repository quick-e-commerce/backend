package com.example.demo.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 16)
    private String username;

    @Column(length = 24)
    private String password;

    private LocalDateTime createdAt;

    @OneToMany
    private List<ProductEntity> wishlist;
}
