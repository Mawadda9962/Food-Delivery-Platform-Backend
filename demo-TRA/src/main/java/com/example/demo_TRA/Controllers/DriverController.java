package com.example.demo_TRA.Controllers;

import com.example.demo_TRA.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("drivers")
public class DriverController {

    @Autowired
    DeliveryService deliveryService;
}
