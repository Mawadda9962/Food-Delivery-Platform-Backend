package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.Entities.DeliveryDriver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDriverResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String vehicleType;
    private boolean isOnline;

    public static DeliveryDriverResponseDTO fromEntity(DeliveryDriver driver) {
        if (driver == null) {
            return null;
        }

        DeliveryDriverResponseDTO dto = new DeliveryDriverResponseDTO();

        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setEmail(driver.getEmail());
        dto.setPhone(driver.getPhone());
        dto.setVehicleType(driver.getVehicleType());
        dto.setOnline(driver.isOnline());

        return dto;
    }

}
