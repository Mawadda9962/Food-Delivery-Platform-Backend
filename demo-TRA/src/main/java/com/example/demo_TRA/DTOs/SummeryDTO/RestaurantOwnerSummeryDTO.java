package com.example.demo_TRA.DTOs.SummeryDTO;

import com.example.demo_TRA.Entities.RestaurantOwner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantOwnerSummeryDTO {
    private String firstName;
    private String lastName;
    private String email;

    public static RestaurantOwnerSummeryDTO fromEntity(RestaurantOwner owner) {

        RestaurantOwnerSummeryDTO dto = new RestaurantOwnerSummeryDTO();

        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setEmail(owner.getEmail());

        return dto;
    }
}
