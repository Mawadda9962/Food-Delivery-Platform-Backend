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
public class MenuItem extends BaseEntity{
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private Boolean isVegetarian;
    private Integer calories;


    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menuItem")
    private List<OrderItem> orderItems;

}
