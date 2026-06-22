package com.example.demo_TRA.DTOs;

import com.example.demo_TRA.Entities.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerRequestDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String customerEmail;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private Integer loyaltyPoints;
    private String customerCode;


    public Customer toEntity(){
        Customer customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCustomerEmail(customerEmail);
        customer.setPhone(phone);
        customer.setCustomerCode(customerCode);






        public static ApplicantDTO convertToDTO(Applicant applicant){
            ApplicantDTO dto = new ApplicantDTO();

            dto.setId(applicant.getId());
            dto.setFirstName(applicant.getFirstName());
            dto.setLastName(applicant.getLastName());
            dto.setNationality(applicant.getNationality());
            dto.setPassportNumber(applicant.getPassportNumber());
            dto.setCriminalRecorde(applicant.isCriminalRecorde());
            return dto;
        }

        public static List<ApplicantDTO> convertToDTO(List<Applicant> applicants){
            List<ApplicantDTO> dtos = new ArrayList<>();
            for (Applicant applicant : applicants){
                dtos.add(convertToDTO(applicant));
            }
            return dtos;
        }

    }

}
