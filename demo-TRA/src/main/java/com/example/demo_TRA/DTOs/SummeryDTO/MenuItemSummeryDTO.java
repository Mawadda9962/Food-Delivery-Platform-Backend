package com.example.demo_TRA.DTOs.SummeryDTO;

import com.example.demo_TRA.Entities.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemSummeryDTO {

    private String name;
    private Double price;
    private Boolean isAvailable;

    public static MenuItemSummeryDTO fromEntity(MenuItem menuItem) {

        MenuItemSummeryDTO dto = new MenuItemSummeryDTO();

        dto.setName(menuItem.getName());
        dto.setPrice(menuItem.getPrice());
        dto.setIsAvailable(menuItem.getIsAvailable());

        return dto;
    }

}
