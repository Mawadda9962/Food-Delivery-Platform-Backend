package com.example.demo_TRA.DTOs;

import com.example.demo_TRA.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {
    private String firstName;
    private String lastName;
    private String customerEmail;
    private String phone;
    private Integer loyaltyPoints;
    private String customerCode;


    public static CustomerResponseDTO fromEntity(Customer customer){
        CustomerResponseDTO dto = new CustomerResponseDTO();

        customer.setFirstName(customer.getFirstName());
        customer.setLastName(customer.getLastName());
        customer.setCustomerEmail(customer.getCustomerEmail());
        customer.setPhone(customer.getPhone());
        customer.setCustomerCode(customer.getCustomerCode());
        customer.setLoyaltyPoints(customer.getLoyaltyPoints());
        return dto;
    }
}
