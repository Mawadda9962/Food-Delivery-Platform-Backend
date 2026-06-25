package com.example.demo.TRA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Delivery extends BaseEntity{
    private String trackingCode;
    private String status;
    private LocalDateTime assignedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    @OneToOne
    private Order order;

    @ManyToOne
    private DeliveryDriver deliveryDriver;
}
