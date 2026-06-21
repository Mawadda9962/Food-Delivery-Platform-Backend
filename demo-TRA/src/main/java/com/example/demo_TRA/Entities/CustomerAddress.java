package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class CustomerAddress {
  private String street;
  private String city;
  private String building;





}
