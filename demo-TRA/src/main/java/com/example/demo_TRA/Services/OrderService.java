package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.RequestDTO.OrderRequestDTO;
import com.example.demo_TRA.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

}
