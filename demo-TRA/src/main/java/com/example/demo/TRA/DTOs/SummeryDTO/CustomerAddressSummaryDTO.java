package com.example.demo.TRA.DTOs.SummeryDTO;

import com.example.demo.TRA.Entities.CustomerAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressSummaryDTO   {
    private String street;
    private String city;

    public static CustomerAddressSummaryDTO fromEntity(CustomerAddress customerAddress) {

        CustomerAddressSummaryDTO dto = new CustomerAddressSummaryDTO();

        dto.setStreet(customerAddress.getStreet());
        dto.setCity(customerAddress.getCity());

        return dto;
    }
}
