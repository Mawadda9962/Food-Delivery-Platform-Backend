package com.example.demo.TRA.DTOs.SummeryDTO;

import com.example.demo.TRA.Entities.Customer;
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

        CustomerSummaryDTO dto = new CustomerSummaryDTO();

        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setCustomerCode(customer.getCustomerCode());

        return dto;
    }
}
