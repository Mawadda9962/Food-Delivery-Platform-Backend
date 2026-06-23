package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.DTOs.SummeryDTO.RestaurantSummaryDTO;
import com.example.demo_TRA.Entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {
    private Long id;
    private String name;
    private String cuisineType;
    private Double deliveryFee;
    private Boolean acceptingOrders;

    public static RestaurantSummaryDTO fromEntity(Restaurant restaurant){
        if (restaurant == null){
            return null;
        }
    }



}
