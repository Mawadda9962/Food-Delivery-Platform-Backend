package com.example.demo_TRA.DTOs.ResponseDTO;


import com.example.demo_TRA.Entities.ComboMeal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboMealResponseDTO {
    private String comboName;
    private String description;
    private Double totalPrice;
    private Boolean isAvailable;

    public static ComboMealResponseDTO fromEntity(ComboMeal comboMeal) {
        if (comboMeal == null) {
            return null;
        }

        ComboMealResponseDTO dto = new ComboMealResponseDTO();

        dto.setComboName(comboMeal.getComboName());
        dto.setDescription(comboMeal.getDescription());
        dto.setTotalPrice(comboMeal.getTotalPrice());
        dto.setIsAvailable(comboMeal.getIsAvailable());
        return dto;
    }

    public static List<ComboMealResponseDTO> fromEntity(List<ComboMeal> comboMeals) {
        List<ComboMealResponseDTO> dtos = new ArrayList<>();
        if (comboMeals != null) {
            for (ComboMeal comboMeal : comboMeals) {
                dtos.add(fromEntity(comboMeal));
            }
        }
        return dtos;
    }

}
