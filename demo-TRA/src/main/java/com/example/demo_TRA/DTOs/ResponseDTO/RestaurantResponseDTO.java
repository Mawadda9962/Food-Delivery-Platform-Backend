package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.DTOs.SummeryDTO.RestaurantSummaryDTO;
import com.example.demo_TRA.Entities.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponseDTO {

    private String name;
    private String cuisineType;
    private Double deliveryFee;
    private Boolean acceptingOrders;

    public static RestaurantResponseDTO fromEntity(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }

        RestaurantResponseDTO dto = new RestaurantResponseDTO();

        dto.setName(restaurant.getName());
        dto.setCuisineType(restaurant.getCuisineType());
        dto.setDeliveryFee(restaurant.getDeliveryFee());
        dto.setAcceptingOrders(restaurant.getAcceptingOrders());

        return dto;
    }

    public static List<RestaurantResponseDTO> fromEntity(List<Restaurant> restaurants){
        List<RestaurantResponseDTO> dtos = new ArrayList<>(); //return type
        if (restaurants != null){
            for (Restaurant restaurant : restaurants){
                dtos.add(fromEntity(restaurant));
            }
        }
        return dtos;
    }


}
