package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.RequestDTO.CustomerAddressRequestDTO;
import com.example.demo_TRA.DTOs.RequestDTO.CustomerRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.CustomerResponseDTO;
import com.example.demo_TRA.Entities.Customer;
import com.example.demo_TRA.Entities.CustomerAddress;
import com.example.demo_TRA.Exceptions.DuplicateResourceException;
import com.example.demo_TRA.Exceptions.ResourceNotFoundException;
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
    public CustomerResponseDTO addAddress(Integer customerId, CustomerAddressRequestDTO address) {
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        CustomerAddress newAddress = address.toEntity();
        newAddress.setCustomer(customer);
        newAddress.setCreateDate(LocalDate.now());
        newAddress.setUpdateDate(LocalDateTime.now());
        newAddress.setIsActive(true);

        customerAddressRepository.save(newAddress);
        return CustomerResponseDTO.fromEntity(customer);
    }

    //updateLoyaltyPoints
    public CustomerResponseDTO updateLoyaltyPoints(Integer customerId, int points) {
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);
        customer.setUpdateDate(LocalDateTime.now());

        Customer saved = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(saved);
    }

    //apply Loyalty Penalty
    public CustomerResponseDTO applyLoyaltyPenalty(Integer customerId, int pointsDeducted) {
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        //Calculates what the new loyalty point total
        int newPoints = customer.getLoyaltyPoints() - pointsDeducted;
        customer.setLoyaltyPoints(Math.max(newPoints, 0));
        customer.setUpdateDate(LocalDateTime.now());

        Customer saved = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(saved);
    }

    //deactivate Customer(Soft Delete)
    public void deactivateCustomer(Integer customerId) {
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        customer.setIsActive(false);
        customer.setUpdateDate(LocalDateTime.now());
        customerRepository.save(customer);
    }
}
