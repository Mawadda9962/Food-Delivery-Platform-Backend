package com.example.demo_TRA.DTOs.ResponseDTO;

import com.example.demo_TRA.Entities.CorporateOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateOrderResponseDTO {
    private String corporateCode;
    private String companyName;
    private String costCenter;
    private LocalDate orderDate;
    private String status;
    private Double totalAmount;

    public static CorporateOrderResponseDTO fromEntity(CorporateOrder corporateOrder) {
        if (corporateOrder == null) {
            return null;
        }

        CorporateOrderResponseDTO dto = new CorporateOrderResponseDTO();

        dto.setCorporateCode(corporateOrder.getCorporateCode());
        dto.setCompanyName(corporateOrder.getCompanyName());
        dto.setCostCenter(corporateOrder.getCostCenter());
        dto.setOrderDate(corporateOrder.getOrderDate());
        dto.setStatus(corporateOrder.getStatus());
        dto.setTotalAmount(corporateOrder.getTotalAmount());
        return dto;
    }
}
