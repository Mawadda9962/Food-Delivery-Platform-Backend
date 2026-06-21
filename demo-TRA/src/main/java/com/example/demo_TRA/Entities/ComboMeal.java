package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class ComboMeal extends BaseEntity{
    private String comboName;
    private String description;
    private Double totalPrice;
    private Boolean isAvailable;

    @ManyToOne
    private Restaurant restaurant;

    @ManyToMany
    private List<MenuItem> menuItems;
}
