package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.RequestDTO.CustomerRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.CustomerResponseDTO;
import com.example.demo_TRA.Entities.Customer;
import com.example.demo_TRA.Exceptions.DuplicateResourceException;
import com.example.demo_TRA.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerResponseDTO customerResponseDTO(CustomerRequestDTO dto){
        List<Customer> existingCustomers  = customerRepository.findByEmail(dto.getCustomerEmail());

        if (!existingCustomers.isEmpty()){
            throw new DuplicateResourceException("Customer with email" + dto.getCustomerEmail() + "already exists");
        }


    }
}
