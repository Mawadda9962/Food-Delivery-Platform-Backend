package com.example.demo.TRA.DTOs.SummeryDTO;

import com.example.demo.TRA.Entities.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliverySummeryDTO {
    private String trackingCode;
    private String status;

    public static DeliverySummeryDTO fromEntity(Delivery delivery) {

        DeliverySummeryDTO dto = new DeliverySummeryDTO();

        dto.setTrackingCode(delivery.getTrackingCode());
        dto.setStatus(delivery.getStatus());

        return dto;
    }

}
