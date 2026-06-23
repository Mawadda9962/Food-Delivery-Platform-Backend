package com.example.demo_TRA.DTOs.SummeryDTO;

import com.example.demo_TRA.Entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantSummaryDTO {
    private String name;
    private String cuisineType;
    private Double deliveryFee;
    private Boolean acceptingOrders;

    public static RestaurantSummaryDTO fromEntity(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }

        RestaurantSummaryDTO dto = new RestaurantSummaryDTO();

        dto.setName(restaurant.getName());
        dto.setCuisineType(restaurant.getCuisineType());
        dto.setDeliveryFee(restaurant.getDeliveryFee());
        dto.setAcceptingOrders(restaurant.getAcceptingOrders());

        return dto;
    }

}
