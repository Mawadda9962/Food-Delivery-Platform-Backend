package com.example.demo_TRA.DTOs.SummeryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummeryDTO {

    private String orderCode;
    private LocalDate orderDate;
    private String status;
    private Double totalAmount;


}
