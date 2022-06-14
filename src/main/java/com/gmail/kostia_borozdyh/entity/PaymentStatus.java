package com.gmail.kostia_borozdyh.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "payment_status")
public class PaymentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    private String status;
}
