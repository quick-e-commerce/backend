package com.example.demo.database;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "delivery")
@Getter
public class DeliveryEntity {
    @Id
    @GeneratedValue
    private Integer Id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private Integer cost;
    @Column(length = 512)
    private String recipientAddress;
    @Column(length = 32)
    private String recipientPhoneNumber;
    @Column(length = 64)
    private String recipientEmail;
    @Column(length = 64)
    private String recipientFirstName;
    @Column(length = 64)
    private String recipientLastName;
}
