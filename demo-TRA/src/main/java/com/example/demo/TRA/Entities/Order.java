package com.example.demo.TRA.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
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

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    @OneToOne(mappedBy = "order")
    private Payment payment;

}
