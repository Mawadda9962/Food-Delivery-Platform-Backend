package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.RequestDTO.CustomerAddressRequestDTO;
import com.example.demo_TRA.DTOs.RequestDTO.CustomerRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.CustomerResponseDTO;
import com.example.demo_TRA.Entities.Customer;
import com.example.demo_TRA.Entities.CustomerAddress;
import com.example.demo_TRA.Exceptions.DuplicateResourceException;
import com.example.demo_TRA.Repositories.CustomerAddressRepository;
import com.example.demo_TRA.Repositories.CustomerRepository;
import com.example.demo_TRA.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAddressRepository customerAddressRepository;


    //Create Customer
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto){
        List<Customer> existingCustomers  = customerRepository.findByEmail(dto.getCustomerEmail());

        if (!existingCustomers.isEmpty()){
            throw new DuplicateResourceException("Customer with email" + dto.getCustomerEmail() + "already exists");
        }

        Customer customer = dto.toEntity();
        customer.setCustomerCode(HelperUtils.generateCode("CUST"));
        customer.setCreateDate(LocalDate.now());
        customer.setUpdateDate(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(savedCustomer);
    }


    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto, CustomerAddressRequestDTO initialAddress){
        List<Customer> existingCustomers = customerRepository.findByEmail(dto.getCustomerEmail());

        if (!existingCustomers.isEmpty()){
            throw new DuplicateResourceException("Customer with email " + dto.getCustomerEmail() + "already exists");
        }

        Customer customer = dto.toEntity();

        customer.setCustomerCode(HelperUtils.generateCode("CUST"));
        customer.setCreateDate(LocalDate.now());
        customer.setUpdateDate(LocalDateTime.now());
        customer.setIsActive(true);

        Customer savedCustomer = customerRepository.save(customer);

        CustomerAddress address = initialAddress.toEntity();
        address.setCustomer(savedCustomer);
        address.setIsDefault(true);
        address.setCreateDate(LocalDate.now());
        address.setUpdateDate(LocalDateTime.now());
        address.setIsActive(true);

        customerAddressRepository.save(address);

        return CustomerResponseDTO.fromEntity(savedCustomer);

    }

    //add Address

    public CustomerResponseDTO addAddress(Integer customerId, CustomerAddressRequestDTO address ){

    }
}
