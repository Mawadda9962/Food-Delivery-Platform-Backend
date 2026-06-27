package com.example.demo.TRA.Controllers;

import com.example.demo.TRA.DTOs.RequestDTO.CorporateOrderRequestDTO;
import com.example.demo.TRA.DTOs.RequestDTO.OrderItemRequestDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.CorporateOrderResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.OrderResponseDTO;
import com.example.demo.TRA.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    // Initialize an empty order → 201
    @PostMapping("/customer/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<OrderResponseDTO> createOrder(@PathVariable Integer customerId, @PathVariable Integer restaurantId) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(customerId, restaurantId, null, null));
    }

    // Add an item to the order
    @PostMapping("/{id}/items")
    public ResponseEntity<OrderResponseDTO> addMenuItemToOrder(
            @PathVariable Integer id, @RequestBody OrderItemRequestDTO item) {

        return ResponseEntity.ok(orderService.addMenuItemToOrder(id, item.getMenuItemId(), item.getQuantity()));
    }

    // Soft-delete an item from the order → 204
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<Void> removeMenuItemFromOrder(@PathVariable Integer id, @PathVariable Integer itemId) {

        orderService.removeMenuItemFromOrder(id, itemId);
        return ResponseEntity.noContent().build();
    }

    // Apply a discount to the order
    @PutMapping("/{id}/discount/{amount}")
    public ResponseEntity<OrderResponseDTO> applyDiscount(@PathVariable Integer id, @PathVariable double amount) {

        return ResponseEntity.ok(orderService.applyDiscount(id, amount));
    }

    // Lock the order — no more items can be added
    @PutMapping("/{id}/confirm")
    public ResponseEntity<OrderResponseDTO> confirmOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.confirmOrder(id));
    }

    // Update order status (PREPARING, READY, ...)
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Integer id, @PathVariable String status) {

        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // Cancel order — 409 if not PENDING
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    // Get full order details (with items)
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Get active orders for a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByRestaurant(@PathVariable Integer restaurantId, @RequestParam String status) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurantAndStatus(restaurantId, status));
    }

    // Create a corporate order → 201
    @PostMapping("/corporate")
    public ResponseEntity<CorporateOrderResponseDTO> placeCorporateOrder(@RequestBody CorporateOrderRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeCorporateOrder(dto));
    }

    //Extended Use-Case
    // Get Order Timeline
    @GetMapping("timeline")
    public ResponseEntity<Map<String, Object>> getOrderTimeline(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderTimeline(id));
    }

    //Duplicate a past order as a new PENDING order
    @PostMapping("reorder")
    public ResponseEntity<OrderResponseDTO> reorder(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.reorder(id));
    }

    // get Order Eta
    @GetMapping("eta")
    public ResponseEntity<Map<String, Object>> getOrderEta(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderEta(id));
    }

}
