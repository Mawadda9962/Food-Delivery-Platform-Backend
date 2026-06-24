package com.example.demo_TRA.Services;

import com.example.demo_TRA.Repositories.DeliveryDriverRepository;
import com.example.demo_TRA.Repositories.DeliveryRepository;
import com.example.demo_TRA.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;

    @Autowired
    private OrderRepository orderRepository;


}
