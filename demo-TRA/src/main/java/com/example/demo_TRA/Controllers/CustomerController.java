package com.example.demo_TRA.Controllers;

import com.example.demo_TRA.DTOs.RequestDTO.CustomerAddressRequestDTO;
import com.example.demo_TRA.DTOs.RequestDTO.CustomerRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.CustomerAddressResponseDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.CustomerResponseDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.OrderResponseDTO;
import com.example.demo_TRA.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    //Create new customer
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(dto));
    }

    //Get All Customers
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    //Get Customer By ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> GetCustomerById(@PathVariable Integer id ){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //Get Customer By Email
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> GetCustomerByEmail(@PathVariable String email){
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }


    //Soft delete customer
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCustomer(@PathVariable Integer id) {
        customerService.deactivateCustomer(id);
        return ResponseEntity.noContent().build();
    }

    //Add loyalty points
    @PutMapping("/{id}/loyalty/add/{points}")
    public ResponseEntity<CustomerResponseDTO> addLoyaltyPoints(@PathVariable Integer id,
                                                                @PathVariable int points) {
        return ResponseEntity.ok(customerService.updateLoyaltyPoints(id,points));
    }

    //Deduct loyalty points
    @PutMapping("/{id}/loyalty/deduct/{points}")
    public ResponseEntity<CustomerResponseDTO> deductLoyaltyPoints(@PathVariable Integer id,
                                                                   @PathVariable int points) {
        return ResponseEntity.ok(customerService.applyLoyaltyPenalty(id, points));
    }

    //Add new address to a customer
    @PostMapping("/{id}/addresses")
    public ResponseEntity<CustomerResponseDTO> addAddress(@PathVariable Integer id, @RequestBody CustomerAddressRequestDTO dto){
        CustomerResponseDTO update = customerService.addAddress(id,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(update);
    }

    //Get all address for customer
    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<CustomerAddressResponseDTO>> getCustomerAddress(@PathVariable Integer id){
        return ResponseEntity.ok(customerService.getCustomerAddresses(id));
    }

    //Set an Address as default
    @PutMapping("/addresses/{addressId}/default")
    public ResponseEntity<CustomerAddressResponseDTO> setDefaultAddress(@PathVariable Integer addressId) {
        return ResponseEntity.ok(customerService.setDefaultAddress(addressId));
    }

    //Soft-delete an address
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId) {
        customerService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    //Get all orders for this customer
    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(@PathVariable Integer id) {
        return ResponseEntity.ok(customerService.getCustomerOrders(id));
    }





}
