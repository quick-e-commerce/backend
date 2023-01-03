package com.example.demo.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@Getter
public class ShopEntity {
    @Id
    @GeneratedValue
    private Integer Id;
    @Column(length = 32)
    private String name;
    @Column(length = 512)
    private String address;
    @Column(length = 32)
    private String phoneNumber;
    @Column(length = 128)

    private String logoThumbnailUrl;
}
