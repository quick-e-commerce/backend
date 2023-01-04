package com.example.demo.database;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Getter
public class ProductEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shopEntity;
    @Column(length = 32)
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;
    @Column(length = 4096)
    private String descriptionHtml;
    private Float fullPrice;
    private Float discountPrice;
    @Column(length = 128)
    private String thumbnailUrl;
    private LocalDateTime createdAt;
}
