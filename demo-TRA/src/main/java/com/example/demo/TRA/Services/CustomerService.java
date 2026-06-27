package com.example.demo.TRA.Services;

import com.example.demo.TRA.DTOs.CustomerPatchDTO;
import com.example.demo.TRA.DTOs.RequestDTO.CustomerAddressRequestDTO;
import com.example.demo.TRA.DTOs.RequestDTO.CustomerRequestDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.CustomerAddressResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.CustomerResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.OrderResponseDTO;
import com.example.demo.TRA.Entities.Customer;
import com.example.demo.TRA.Entities.CustomerAddress;
import com.example.demo.TRA.Exceptions.DuplicateResourceException;
import com.example.demo.TRA.Exceptions.ResourceNotFoundException;
import com.example.demo.TRA.Repositories.CustomerAddressRepository;
import com.example.demo.TRA.Repositories.CustomerRepository;
import com.example.demo.TRA.Repositories.OrderRepository;
import com.example.demo.TRA.Utils.HelperUtils;
import com.example.demo.TRA.Entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAddressRepository customerAddressRepository;

    @Autowired
    OrderRepository orderRepository;


    //Create Customer
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto){
        Optional<Customer> existingCustomers  = customerRepository.findByEmail(dto.getCustomerEmail());

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
        Optional<Customer> existingCustomers = customerRepository.findByEmail(dto.getCustomerEmail());

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

    //Get All Customers
    public List<CustomerResponseDTO> getAllCustomers(){
        List<Customer> customers = customerRepository.findAllActiveCustomers();
        return CustomerResponseDTO.fromEntity(customers);
    }

    //Get Customer ById
    public CustomerResponseDTO getCustomerById(Integer customerId){
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        return CustomerResponseDTO.fromEntity(customer);
    }

    //Get Customer By Email
    public CustomerResponseDTO getCustomerByEmail(String email){
        Customer customers  = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));

        return CustomerResponseDTO.fromEntity(customers);
    }

    //Taking the address by default
    public CustomerAddressResponseDTO setDefaultAddress(Integer addressId) {

        CustomerAddress address = customerAddressRepository.findActiveById(addressId)
                        .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId));

        Integer customerId = address.getCustomer().getId();

        List<CustomerAddress> addresses = customerAddressRepository.findByCustomerId(customerId);

        for (CustomerAddress addr : addresses) {
            addr.setIsDefault(false);customerAddressRepository.save(addr);
        }
        address.setIsDefault(true);
        address.setUpdateDate(LocalDateTime.now());

        CustomerAddress saved = customerAddressRepository.save(address);
        return CustomerAddressResponseDTO.fromEntity(saved);
    }

    //get Customer Address
    public List<CustomerAddressResponseDTO> getCustomerAddresses(
            Integer customerId) {

        customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with ID: " + customerId));

        List<CustomerAddress> addresses =
                customerAddressRepository.findByCustomerId(customerId);

        return CustomerAddressResponseDTO.fromEntity(addresses);
    }

    public void deleteAddress(Integer addressId) {

        CustomerAddress address =
                customerAddressRepository.findActiveById(addressId)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Address not found with ID: " + addressId));

        address.setIsActive(false);
        address.setUpdateDate(LocalDateTime.now());

        customerAddressRepository.save(address);
    }


    public List<OrderResponseDTO> getCustomerOrders(Integer customerId) {

        customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Customer not found with ID: " + customerId));

        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return OrderResponseDTO.fromEntity(orders);
    }


    //Extended Use-Case
    //Search Customer
    public Page<CustomerResponseDTO> searchCustomers(String name, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customers = customerRepository.searchCustomers(name, pageable);

        return customers.map(CustomerResponseDTO::fromEntity);
    }

    //Partial Update Customer
    public CustomerResponseDTO patchCustomer(Integer customerId, CustomerPatchDTO dto) {

        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        if (dto.getFirstName() != null) {
            customer.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            customer.setLastName(dto.getLastName());
        }
        if (dto.getPhone() != null) {
            customer.setPhone(dto.getPhone());
        }
        if (dto.getCustomerEmail() != null) {
            Optional<Customer> existingCustomer = customerRepository.findByEmail(dto.getCustomerEmail());

            if (existingCustomer.isPresent()
                    && !existingCustomer.get().getId().equals(customerId)) {

                throw new DuplicateResourceException(
                        "Customer with email " + dto.getCustomerEmail() + " already exists");
            }
            customer.setCustomerEmail(dto.getCustomerEmail());
        }
        customer.setUpdateDate(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(savedCustomer);
    }





}
