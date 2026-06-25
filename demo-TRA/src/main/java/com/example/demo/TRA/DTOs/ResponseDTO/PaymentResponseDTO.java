package com.example.demo.TRA.DTOs.ResponseDTO;

import com.example.demo.TRA.Entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private String paymentMethod;
    private String status;
    private Double amount;
    private String transactionRef;
    private LocalDateTime processedAt;

    public static PaymentResponseDTO fromEntity(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setAmount(payment.getAmount());
        dto.setTransactionRef(payment.getTransactionRef());
        dto.setProcessedAt(payment.getProcessedAt());

        return dto;
    }

    public static List<PaymentResponseDTO> fromEntity(List<Payment> payments) {
        List<PaymentResponseDTO> dtos = new ArrayList<>();
        if (payments != null) {
            for (Payment payment : payments) {
                dtos.add(fromEntity(payment));
            }
        }
        return dtos;
    }
}
