package com.example.demo_TRA.DTOs;

import com.example.demo_TRA.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSummaryDTO {

    private String firstName;
    private String lastName;
    private String customerCode;

    public static CustomerSummaryDTO fromEntity(Customer customer){

    }
}
