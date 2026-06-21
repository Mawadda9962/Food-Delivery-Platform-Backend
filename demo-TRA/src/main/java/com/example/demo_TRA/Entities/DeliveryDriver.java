package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class DeliveryDriver extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String passwordHash;
    private String driverCode;
    private String vehicleType;
    private String vehiclePlate;
    private String currentLate;
    private String currentLng;
    private boolean isOnline;

    @OneToMany(mappedBy = "deliveryDriver")
    private List<Delivery> deliveries ;

}
