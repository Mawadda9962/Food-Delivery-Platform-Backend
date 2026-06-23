package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Payment extends BaseEntity {
    public String paymentMethod;
    public String status;
    public Double amount;
    private String transactionRef;
    private LocalDateTime processedAt;

    @OneToOne
    private Order order;


}
