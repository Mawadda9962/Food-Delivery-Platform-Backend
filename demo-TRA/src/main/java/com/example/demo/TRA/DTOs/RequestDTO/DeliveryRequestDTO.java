package com.example.demo.TRA.DTOs.RequestDTO;

import com.example.demo.TRA.Entities.Delivery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DeliveryRequestDTO {
    @NotBlank(message = "Tracking code is required")
    private String trackingCode;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDateTime assignedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    @NotNull(message = "Order ID is required")
    private Integer orderId;

    public Delivery toEntity() { // For Creating
        Delivery delivery = new Delivery();

        delivery.setTrackingCode(trackingCode);
        delivery.setStatus(status);
        delivery.setAssignedAt(assignedAt);
        delivery.setPickedUpAt(pickedUpAt);
        delivery.setDeliveredAt(deliveredAt);

        return delivery;
    }

    public void applyTo(Delivery delivery) { // For Updating
        delivery.setTrackingCode(trackingCode);
        delivery.setStatus(status);
        delivery.setAssignedAt(assignedAt);
        delivery.setPickedUpAt(pickedUpAt);
        delivery.setDeliveredAt(deliveredAt);
    }
}
