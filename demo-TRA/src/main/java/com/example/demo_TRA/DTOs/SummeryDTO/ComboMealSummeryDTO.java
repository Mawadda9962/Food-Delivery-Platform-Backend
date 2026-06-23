package com.example.demo_TRA.DTOs.SummeryDTO;

import com.example.demo_TRA.Entities.ComboMeal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboMealSummeryDTO {

    private String comboName;
    private Double totalPrice;
    private Boolean isAvailable;

    public static ComboMealSummeryDTO fromEntity(ComboMeal comboMeal) {

        ComboMealSummeryDTO dto = new ComboMealSummeryDTO();

        dto.setComboName(comboMeal.getComboName());
        dto.setTotalPrice(comboMeal.getTotalPrice());
        dto.setIsAvailable(comboMeal.getIsAvailable());

        return dto;
    }
}
