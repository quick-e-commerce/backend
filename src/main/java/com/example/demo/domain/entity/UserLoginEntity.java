package com.example.demo.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_login")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginEntity {
    @Id
    @NonNull
    private Integer userId;

    @MapsId
    @OneToOne
    @JoinColumn
    private UserEntity user;

    @NonNull
    private String token;

    @NonNull
    private LocalDateTime expiresAt;
}
