package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Payment extends BaseEntity {
    public String paymentMethod;
    public String status;

}
