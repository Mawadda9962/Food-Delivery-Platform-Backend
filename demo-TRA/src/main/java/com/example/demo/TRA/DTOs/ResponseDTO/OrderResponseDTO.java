package com.example.demo.TRA.DTOs.ResponseDTO;

import com.example.demo.TRA.Entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private String orderCode;
    private LocalDate orderDate;
    private String status;
    private Double subtotal;
    private Double deliveryFee;
    private Double discountAmount;
    private Double totalAmount;
    private String deliveryNotes;

    public static OrderResponseDTO fromEntity(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderCode(order.getOrderCode());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setSubtotal(order.getSubtotal());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliveryNotes(order.getDeliveryNotes());

        return dto;
    }

    public static List<OrderResponseDTO> fromEntity(List<Order> orders) {
        List<OrderResponseDTO> dtos = new ArrayList<>();
        if (orders != null) {
            for (Order order : orders) {
                dtos.add(fromEntity(order));
            }
        }
        return dtos;
    }

}

