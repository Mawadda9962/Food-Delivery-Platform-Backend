package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class CorporateOrder extends BaseEntity{
    private String corporateCode;
    private String companyName;
    private String costCenter;
    private LocalDate orderDate;
    private



}
