package com.example.demo.TRA.DTOs.RequestDTO;

import com.example.demo.TRA.Entities.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantRequestDTO {

    @NotBlank(message = "Restaurant name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Cuisine type is required")
    private String cuisineType;

    @NotBlank(message = "Opening time is required")
    private String openingTime;

    @NotBlank(message = "Closing time is required")
    private String closingTime;

    @NotNull(message = "Minimum order amount is required")
    private Double minOrderAmount;

    @NotNull(message = "Delivery fee is required")
    private Double deliveryFee;

    private Boolean acceptingOrders;

    public Restaurant toEntity() { //For Creating
        Restaurant restaurant = new Restaurant();

        restaurant.setName(name);
        restaurant.setDescription(description);
        restaurant.setCuisineType(cuisineType);
        restaurant.setOpeningTime(openingTime);
        restaurant.setClosingTime(closingTime);
        restaurant.setMinOrderAmount(minOrderAmount);
        restaurant.setDeliveryFee(deliveryFee);
        restaurant.setAcceptingOrders(acceptingOrders);

        return restaurant;
    }

    public void applyTo(Restaurant restaurant) { //For Updating
        restaurant.setName(name);
        restaurant.setDescription(description);
        restaurant.setCuisineType(cuisineType);
        restaurant.setOpeningTime(openingTime);
        restaurant.setClosingTime(closingTime);
        restaurant.setMinOrderAmount(minOrderAmount);
        restaurant.setDeliveryFee(deliveryFee);
        restaurant.setAcceptingOrders(acceptingOrders);
    }
}
