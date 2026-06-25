package com.example.demo.TRA.Entities;

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
    private String customerEmail;
    private String phone;
    private String passwordHash;
    private Integer loyaltyPoints;
    private String customerCode;


    @OneToMany(mappedBy = "customer")
    private List<CustomerAddress> addresses;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;
}
