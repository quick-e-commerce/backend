package com.example.demo.database;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_access_token")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccessToken {
    @Id
    @NonNull
    private Integer userId;

    @Column
    @NonNull
    private String token;

    @Column
    @NonNull
    private LocalDateTime createdAt;

    @Column
    @NonNull
    private LocalDateTime expiration;
}
