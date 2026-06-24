package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.ResponseDTO.DeliveryResponseDTO;
import com.example.demo_TRA.Entities.Delivery;
import com.example.demo_TRA.Entities.DeliveryDriver;
import com.example.demo_TRA.Entities.Order;
import com.example.demo_TRA.Exceptions.InvalidOrderStateException;
import com.example.demo_TRA.Exceptions.ResourceNotFoundException;
import com.example.demo_TRA.Repositories.DeliveryDriverRepository;
import com.example.demo_TRA.Repositories.DeliveryRepository;
import com.example.demo_TRA.Repositories.OrderRepository;
import com.example.demo_TRA.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryDriverRepository deliveryDriverRepository;

    @Autowired
    private OrderRepository orderRepository;


    //Assign Driver To Order
    public DeliveryResponseDTO assignDriverToOrder(Integer orderId, Integer driverId) {

        Order order = orderRepository.findActiveById(orderId).orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with ID: " + orderId));

        DeliveryDriver driver = deliveryDriverRepository.findActiveById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with ID: " + driverId));

        if (!driver.isOnline()) {
            throw new InvalidOrderStateException(
                    "Driver is not online and cannot be assigned.");
        }

        Delivery delivery = new Delivery();
        delivery.setTrackingCode(HelperUtils.generateCode("TRK"));
        delivery.setStatus("ASSIGNED");
        delivery.setAssignedAt(LocalDateTime.now());
        delivery.setOrder(order);
        delivery.setDeliveryDriver(driver);
        delivery.setIsActive(true);
        delivery.setCreateDate(LocalDate.now());
        delivery.setUpdateDate(LocalDateTime.now());

        return DeliveryResponseDTO.fromEntity(deliveryRepository.save(delivery));
    }

    // Auto Assign Driver
    public DeliveryResponseDTO autoAssignDriver(Integer orderId) {

        Order order = orderRepository.findActiveById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with ID: " + orderId));

        List<DeliveryDriver> onlineDrivers =
                deliveryDriverRepository.findOnlineDrivers();

        if (onlineDrivers.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No online drivers available");
        }

        DeliveryDriver driver = onlineDrivers.get(0);

        Delivery delivery = new Delivery();
        delivery.setTrackingCode(HelperUtils.generateCode("TRK"));
        delivery.setStatus("ASSIGNED");
        delivery.setAssignedAt(LocalDateTime.now());
        delivery.setOrder(order);
        delivery.setDeliveryDriver(driver);
        delivery.setIsActive(true);
        delivery.setCreateDate(LocalDate.now());
        delivery.setUpdateDate(LocalDateTime.now());

        Delivery savedDelivery = deliveryRepository.save(delivery);

        return DeliveryResponseDTO.fromEntity(savedDelivery);
    }

    //update Driver Location
    public void updateDriverLocation(Integer driverId, double lat, double lng) {

        DeliveryDriver driver = deliveryDriverRepository.findActiveById(driverId).orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with ID: " + driverId));

        driver.setCurrentLat(lat);
        driver.setCurrentLng(lng);

        driver.setUpdateDate(LocalDateTime.now());

        deliveryDriverRepository.save(driver);
    }

    // Mark Delivery Picked Up
    public DeliveryResponseDTO markDeliveryPickedUp(Integer deliveryId) {

        Delivery delivery = deliveryRepository.findActiveById(deliveryId).orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery not found with ID: " + deliveryId));

        delivery.setStatus("PICKED_UP");
        delivery.setPickedUpAt(LocalDateTime.now());
        delivery.setUpdateDate(LocalDateTime.now());

        Delivery savedDelivery = deliveryRepository.save(delivery);

        return DeliveryResponseDTO.fromEntity(savedDelivery);
    }

    // Mark Delivery Delivered
    public DeliveryResponseDTO markDeliveryDelivered(Integer deliveryId) {

        Delivery delivery = deliveryRepository.findActiveById(deliveryId).orElseThrow(() -> new ResourceNotFoundException(
                        "Delivery not found with ID: " + deliveryId));

        delivery.setStatus("DELIVERED");
        delivery.setDeliveredAt(LocalDateTime.now());
        delivery.setUpdateDate(LocalDateTime.now());

        Delivery savedDelivery = deliveryRepository.save(delivery);

        return DeliveryResponseDTO.fromEntity(savedDelivery);
    }

    // Get Deliveries For Driver
    public List<DeliveryResponseDTO> getDeliveriesForDriver(Integer driverId, String status) {

        deliveryDriverRepository.findActiveById(driverId).orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with ID: " + driverId));

        List<Delivery> deliveries = deliveryRepository.findByDeliveryDriverIdAndStatus(driverId, status);
        return DeliveryResponseDTO.fromEntity(deliveries);
    }

    //Changes the driver's availability
}

