package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.Entities.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDTO {
    private String trackingCode;
    private String status;
    private LocalDateTime assignedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;


    public static DeliveryResponseDTO fromEntity(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        DeliveryResponseDTO dto = new DeliveryResponseDTO();

        dto.setTrackingCode(delivery.getTrackingCode());
        dto.setStatus(delivery.getStatus());
        dto.setAssignedAt(delivery.getAssignedAt());
        dto.setPickedUpAt(delivery.getPickedUpAt());
        delivery.setDeliveredAt(delivery.getDeliveredAt());

        return dto;
    }

    public static List<DeliveryResponseDTO> fromEntity(List<Delivery> deliveries) {
        List<DeliveryResponseDTO> dtos = new ArrayList<>();
        if (deliveries != null) {
            for (Delivery delivery : deliveries) {
                dtos.add(fromEntity(delivery));
            }
        }
        return dtos;
    }


}
