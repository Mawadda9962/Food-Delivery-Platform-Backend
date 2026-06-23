package com.example.demo_TRA.DTOs.RequestDTO;

import com.example.demo_TRA.Entities.CorporateOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CorporateOrderRequestDTO {
    @NotBlank(message = "Corporate code is required")
    private String corporateCode;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Cost center is required")
    private String costCenter;

    @NotNull(message = "Order date is required")
    private LocalDate orderDate;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Total amount is required")
    private Double totalAmount;

    public CorporateOrder toEntity() { // For Creating
        CorporateOrder corporateOrder = new CorporateOrder();

        corporateOrder.setCorporateCode(corporateCode);
        corporateOrder.setCompanyName(companyName);
        corporateOrder.setCostCenter(costCenter);
        corporateOrder.setOrderDate(orderDate);
        corporateOrder.setStatus(status);
        corporateOrder.setTotalAmount(totalAmount);

        return corporateOrder;
    }

    public void applyTo(CorporateOrder corporateOrder) { // For Updating
        corporateOrder.setCorporateCode(corporateCode);
        corporateOrder.setCompanyName(companyName);
        corporateOrder.setCostCenter(costCenter);
        corporateOrder.setOrderDate(orderDate);
        corporateOrder.setStatus(status);
        corporateOrder.setTotalAmount(totalAmount);
    }
}
