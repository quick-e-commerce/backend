package com.example.demo.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer shopId;
    private String name;
    private Integer categoryId;
    private String descriptionHtml;
    private Float fullPrice;
    private Float discountPrice;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
}
