package com.example.demo.TRA.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPatchDTO {

    private String firstName;

    private String lastName;

    private String phone;

    private String customerEmail;
}
