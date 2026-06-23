package com.example.demo_TRA.DTOs.RequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuItemRequestDTO {

    @NotBlank(message = "Item name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    private Double price;

    private Boolean isAvailable;
    private Boolean isVegetarian;
    private Integer calories;

    @NotNull(message = "Restaurant ID is required")
    private Long restaurantId;

}
