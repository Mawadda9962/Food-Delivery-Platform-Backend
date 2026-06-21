package com.example.demo_TRA.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity

public class Order extends BaseEntity{
    private String orderCode;
    private LocalDate orderDate;
    private String status;
    private Double subtotal;
    private Double deliveryFee;
    private Double discountAmount;
    private Double totalAmount;
    private String deliveryNotes;


    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;

    @OneToMany
    private List<OrderItem> orderItems;

    @OneToOne
    private Delivery delivery;

    @OneToOne
    private Payment payment;

}
