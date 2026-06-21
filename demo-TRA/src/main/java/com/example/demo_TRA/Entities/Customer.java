package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer passwordHash;
    private String loyaltyPoints;
    private Integer customerCode;

}
