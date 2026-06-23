package com.example.demo_TRA.DTOs.RequestDTO;

import com.example.demo_TRA.Entities.MenuItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    public MenuItem toEntity(){
        MenuItem menuItem = new MenuItem();

        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setIsAvailable(isAvailable);
        menuItem.setIsVegetarian(isVegetarian);
        menuItem.setCalories(calories);

        return menuItem;
    }

    public void applyTo(MenuItem menuItem){
        menuItem.setName(name);
        menuItem.setDescription(description);
        menuItem.setPrice(price);
        menuItem.setIsAvailable(isAvailable);
        menuItem.setIsVegetarian(isVegetarian);
        menuItem.setCalories(calories);
    }
}
