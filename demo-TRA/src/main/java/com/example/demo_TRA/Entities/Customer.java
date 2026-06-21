package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private String CustomerEmail;
    private String phone;
    private String passwordHash;
    private Integer loyaltyPoints;
    private String customerCode;


    @OneToMany
    private List<CustomerAddress> addresses;

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Review> reviews;
}
