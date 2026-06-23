package com.example.demo_TRA.DTOs.RequestDTO;

import com.example.demo_TRA.Entities.RestaurantOwner;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantOwnerRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Password is required")
    private String passwordHash;

    @NotBlank(message = "Business license code is required")
    private String businessLicenseCode;

    public RestaurantOwner toEntity() { // For Creating
        RestaurantOwner owner = new RestaurantOwner();

        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPhone(phone);
        owner.setPasswordHash(passwordHash);
        owner.setBusinessLicenseCode(businessLicenseCode);

        return owner;
    }

    public void applyTo(RestaurantOwner owner) { // For Updating
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setEmail(email);
        owner.setPhone(phone);
        owner.setPasswordHash(passwordHash);
        owner.setBusinessLicenseCode(businessLicenseCode);
    }
}
