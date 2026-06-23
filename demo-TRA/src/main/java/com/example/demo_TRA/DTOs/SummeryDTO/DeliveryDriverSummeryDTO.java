package com.example.demo_TRA.DTOs.SummeryDTO;

import com.example.demo_TRA.Entities.DeliveryDriver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDriverSummeryDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String driverCode;
    private boolean isOnline;

    public static DeliveryDriverSummeryDTO fromEntity(DeliveryDriver driver) {

        DeliveryDriverSummeryDTO dto = new DeliveryDriverSummeryDTO();

        dto.setFirstName(driver.getFirstName());
        dto.setLastName(driver.getLastName());
        dto.setDriverCode(driver.getDriverCode());
        dto.setOnline(driver.isOnline());

        return dto;
    }
}
