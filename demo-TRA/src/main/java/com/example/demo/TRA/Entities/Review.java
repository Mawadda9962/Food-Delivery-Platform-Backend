package com.example.demo.TRA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Review extends BaseEntity{
    private String targetType;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToOne
    private DeliveryDriver deliveryDriver;
}
