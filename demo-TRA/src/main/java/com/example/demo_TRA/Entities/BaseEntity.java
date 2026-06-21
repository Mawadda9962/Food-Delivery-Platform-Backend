package com.example.demo_TRA.Entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class BaseEntity {

    private Integer id;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


}
