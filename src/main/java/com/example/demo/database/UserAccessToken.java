package com.example.demo.database;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_access_token")
@Data
public class UserAccessToken {
    @Id
    private Integer userId;

    @Column
    private String token;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime expiration;
}
