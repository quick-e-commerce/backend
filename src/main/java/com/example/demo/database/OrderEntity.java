package com.example.demo.database;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_order")
@Getter
public class OrderEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userEntity;
    //private String userId;
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity deliveryEntity;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shopEntity;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;
}
