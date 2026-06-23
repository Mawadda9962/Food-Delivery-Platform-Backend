package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.Entities.DeliveryDriver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDriverResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String driverCode;
    private String vehicleType;
    private String vehiclePlate;
    private String currentLate;
    private String currentLng;
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
        dto.setDriverCode(driver.getDriverCode());
        dto.setVehicleType(driver.getVehicleType());
        dto.setVehiclePlate(driver.getVehiclePlate());
        dto.setCurrentLate(driver.getCurrentLate());
        dto.setCurrentLng(driver.getCurrentLng());
        dto.setOnline(driver.isOnline());

        return dto;
    }

    public static List<DeliveryDriverResponseDTO> fromEntity(List<DeliveryDriver> drivers) {
        List<DeliveryDriverResponseDTO> dtos = new ArrayList<>();
        if (drivers != null) {
            for (DeliveryDriver driver : drivers) {
                dtos.add(fromEntity(driver));
            }
        }
        return dtos;
    }
}
