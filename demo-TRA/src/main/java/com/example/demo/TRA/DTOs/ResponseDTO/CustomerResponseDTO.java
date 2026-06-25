package com.example.demo.TRA.DTOs.ResponseDTO;

import com.example.demo.TRA.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
        if (customer == null){
            return null;
        }

        CustomerResponseDTO dto = new CustomerResponseDTO();

        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setCustomerEmail(customer.getCustomerEmail());
        dto.setPhone(customer.getPhone());
        dto.setCustomerCode(customer.getCustomerCode());
        dto.setLoyaltyPoints(customer.getLoyaltyPoints());
        return dto;
    }
    public static List<CustomerResponseDTO> fromEntity(List<Customer> customers) {
        List<CustomerResponseDTO> dtos = new ArrayList<>();
        if (customers != null) {
            for (Customer customer : customers) {
                dtos.add(fromEntity(customer));
            }
        }
        return dtos;
    }
}
