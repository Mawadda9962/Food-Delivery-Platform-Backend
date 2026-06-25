package com.example.demo.TRA.DTOs.ResponseDTO;

import com.example.demo.TRA.Entities.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponseDTO {

    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private Boolean isVegetarian;
    private Integer calories;


    public static MenuItemResponseDTO fromEntity(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }

        MenuItemResponseDTO dto = new MenuItemResponseDTO();

        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setIsAvailable(menuItem.getIsAvailable());
        dto.setIsVegetarian(menuItem.getIsVegetarian());
        dto.setCalories(menuItem.getCalories());

        return dto;
    }

    public static List<MenuItemResponseDTO> fromEntity(List<MenuItem> menuItems) {
        List<MenuItemResponseDTO> dtos = new ArrayList<>();
        if (menuItems != null) {
            for (MenuItem menuItem : menuItems) {
                dtos.add(fromEntity(menuItem));
            }
        }
        return dtos;
    }
}
