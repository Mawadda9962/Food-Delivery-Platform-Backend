package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.RequestDTO.OrderItemRequestDTO;
import com.example.demo_TRA.DTOs.RequestDTO.OrderRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.OrderResponseDTO;
import com.example.demo_TRA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CorporateOrderRepository corporateOrderRepository;


    //Create Order
   public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items){
       return createOrder(customerId, restaurantId, items);
   }

   public OrderResponseDTO createOrder(Integer customerId, Integer restaurantId, List<OrderItemRequestDTO> items, String notes){

   }


}
