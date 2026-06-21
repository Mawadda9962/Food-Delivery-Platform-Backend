package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class CorporateOrder extends BaseEntity{
    private String corporateCode;
    private String companyName;
    private String costCenter;
    private LocalDate orderDate;
    private String status;
    private Double totalAmount;

    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(mappedBy = "corporateOrder")
    private List<OrderItem> orderItems;

}
