package com.example.demo_TRA.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerRequestDTO {

    private String firstName;
    private String lastName;
    private String customerEmail;
    private String phone;
    private Integer loyaltyPoints;
    private String customerCode;

}
