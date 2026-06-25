package com.example.demo_TRA.DTOs.RequestDTO;

import com.example.demo_TRA.Entities.ComboMeal;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComboMealRequestDTO {
    @NotBlank(message = "Combo name is required")
    private String comboName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Total price is required")
    @Min(value = 0, message = "Total price cannot be negative")
    private Double totalPrice;

    private Boolean isAvailable;

    @NotNull(message = "Restaurant ID is required")
    private Integer restaurantId;

    public ComboMeal toEntity() { // For Creating
        ComboMeal comboMeal = new ComboMeal();

        comboMeal.setComboName(comboName);
        comboMeal.setDescription(description);
        comboMeal.setTotalPrice(totalPrice);
        comboMeal.setIsAvailable(isAvailable);

        return comboMeal;
    }

    public void applyTo(ComboMeal comboMeal) { // For Updating
        comboMeal.setComboName(comboName);
        comboMeal.setDescription(description);
        comboMeal.setTotalPrice(totalPrice);
        comboMeal.setIsAvailable(isAvailable);
    }


}
