package com.example.demo_TRA.Entities;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class RestaurantOwner extends BaseEntity{

    private String firstName;
    private String lastName;


}
