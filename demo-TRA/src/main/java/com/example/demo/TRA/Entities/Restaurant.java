package com.example.demo.TRA.Entities;

import jakarta.persistence.Entity;
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
public class Restaurant extends BaseEntity{

    private String name;
    private String description;
    private String cuisineType;//Type of food
    private String openingTime;
    private String closingTime;
    private Double minOrderAmount;
    private Double deliveryFee;
    private Boolean acceptingOrders;


    @ManyToOne
    private RestaurantOwner owner;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems ;

    @OneToMany(mappedBy = "restaurant")
    private List<ComboMeal> comboMeals  ;

}
