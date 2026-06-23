package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.Entities.CustomerAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressResponseDTO {
    private String street;
    private String city;
    private String building;
    private Boolean isDefault;

    public static CustomerAddressResponseDTO fromEntity(CustomerAddress customerAddress){
        if (customerAddress == null){
            return null;
        }

        CustomerAddressResponseDTO dto = new CustomerAddressResponseDTO();

        dto.setStreet(customerAddress.getStreet());
        dto.setCity(customerAddress.getCity());
        dto.setBuilding(customerAddress.getBuilding());
        dto.setIsDefault(customerAddress.getIsDefault());
        return dto;
    }

    public static List<CustomerAddressResponseDTO> fromEntity(List<CustomerAddress> customerAddresses) {
        List<CustomerAddressResponseDTO> dtos = new ArrayList<>();
        if (customerAddresses != null) {
            for (CustomerAddress customerAddress : customerAddresses) {
                dtos.add(fromEntity(customerAddress));
            }
        }
        return dtos;
    }

}
