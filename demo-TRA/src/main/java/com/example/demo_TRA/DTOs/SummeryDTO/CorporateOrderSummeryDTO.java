package com.example.demo_TRA.DTOs.SummeryDTO;

import com.example.demo_TRA.Entities.CorporateOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateOrderSummeryDTO {
    private String corporateCode;
    private String companyName;
    private LocalDate orderDate;
    private String status;
    private Double totalAmount;

    public static CorporateOrderSummeryDTO fromEntity(CorporateOrder corporateOrder) {
        if (corporateOrder == null) {
            return null;
        }

        CorporateOrderSummeryDTO dto = new CorporateOrderSummeryDTO();

        dto.setCorporateCode(corporateOrder.getCorporateCode());
        dto.setCompanyName(corporateOrder.getCompanyName());
        dto.setOrderDate(corporateOrder.getOrderDate());
        dto.setStatus(corporateOrder.getStatus());
        dto.setTotalAmount(corporateOrder.getTotalAmount());

        return dto;
    }

}
