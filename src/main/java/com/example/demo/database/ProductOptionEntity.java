package com.example.demo.database;

import lombok.Getter;

import javax.persistence.*;
@Entity
@Table(name = "product_option")
@Getter
public class ProductOptionEntity {
    @Id @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    @Column(length = 16)
    private String color;
    @Column(length = 8)
    private String size;
    private String stockQuantity;
}
