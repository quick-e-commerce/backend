package com.example.demo.domain.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "customer_order_item")
@Getter
public class OrderItemEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    private Integer quantity;
    private Integer orderPrice;

}
