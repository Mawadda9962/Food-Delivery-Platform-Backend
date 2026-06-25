package com.example.demo_TRA.Controllers;

import com.example.demo_TRA.DTOs.RequestDTO.DeliveryDriverRequestDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.DeliveryDriverResponseDTO;
import com.example.demo_TRA.DTOs.ResponseDTO.DeliveryResponseDTO;
import com.example.demo_TRA.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("drivers")
public class DriverController {

    @Autowired
    DeliveryService deliveryService;

    //Register new driver
    @PostMapping
    public ResponseEntity<DeliveryDriverResponseDTO> registerDriver(@RequestBody DeliveryDriverRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryService.registerDriver(dto));
    }

    //Get All Drivers
    @GetMapping
    public ResponseEntity<List<DeliveryDriverResponseDTO>> getAllDrivers(){
        return ResponseEntity.ok(deliveryService.getAllDrivers());
    }

    //Get Online Drivers
    @GetMapping("/online")
    public ResponseEntity<List<DeliveryDriverResponseDTO>> getOnlineDrivers(){
        return ResponseEntity.ok(deliveryService.getOnlineDrivers());
    }

    //Toggle online status
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> toggleDriverOnlineStatus(@PathVariable Integer id, @RequestParam boolean isOnline) {
        deliveryService.toggleDriverOnlineStatus(id, isOnline);
        return ResponseEntity.noContent().build();
    }

    //Update coordinates
    @PutMapping("/{id}/location")
    public ResponseEntity<Void> updateDriverLocation(@PathVariable Integer id, @RequestParam double lat, @RequestParam double lng) {
        deliveryService.updateDriverLocation(id, lat, lng);
        return ResponseEntity.noContent().build();
    }

    //Get driver's delivery history
    @GetMapping("/{id}/deliveries")
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveriesForDriver(@PathVariable Integer id,
                                                                            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(deliveryService.getDeliveriesForDriver(id, status));
    }

    //Get driver's current active delivery
    @GetMapping("/{id}/deliveries/active")
    public ResponseEntity<DeliveryResponseDTO> getActiveDeliveryForDriver(@PathVariable Integer id) {
        return ResponseEntity.ok(deliveryService.getActiveDeliveryForDriver(id));
    }
}
