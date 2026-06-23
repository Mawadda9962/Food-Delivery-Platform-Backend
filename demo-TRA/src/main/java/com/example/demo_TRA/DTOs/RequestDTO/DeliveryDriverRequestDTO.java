package com.example.demo_TRA.DTOs.RequestDTO;

import com.example.demo_TRA.Entities.DeliveryDriver;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryDriverRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    private boolean isOnline;

    public DeliveryDriver toEntity() { // For Creating
        DeliveryDriver driver = new DeliveryDriver();

        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setEmail(email);
        driver.setPhone(phone);
        driver.setVehicleType(vehicleType);
        driver.setOnline(isOnline);

        return driver;
    }



}
