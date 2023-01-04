package com.example.demo.database;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 16)
    private String username;

    @Column(length = 24)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
