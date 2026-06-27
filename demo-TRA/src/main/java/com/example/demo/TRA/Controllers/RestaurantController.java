package com.example.demo.TRA.Controllers;


import com.example.demo.TRA.DTOs.RequestDTO.ComboMealRequestDTO;
import com.example.demo.TRA.DTOs.RequestDTO.MenuItemRequestDTO;
import com.example.demo.TRA.DTOs.RequestDTO.RestaurantRequestDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.ComboMealResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.MenuItemResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.RestaurantResponseDTO;
import com.example.demo.TRA.Services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    //Create restaurant assigned to an owner
    @PostMapping("/owner/{ownerId}")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@PathVariable Integer ownerId, @RequestBody RestaurantRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createResponse(dto, ownerId));
    }

    //List all restaurants
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    //Get restaurant details
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Integer id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    //Filter by cuisine
    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<RestaurantResponseDTO>> getRestaurantsByCuisine(@PathVariable String cuisine) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCuisine(cuisine));
    }

    //Pause incoming orders
    @PutMapping("/{id}/toggle-orders")
    public ResponseEntity<RestaurantResponseDTO> toggleAcceptingOrders(@PathVariable Integer id, @RequestParam boolean accepting) {
        return ResponseEntity.ok(restaurantService.toggleAcceptingOrders(id, accepting));
    }

    //Update delivery fee
    @PutMapping("/{id}/fee/{newFee}")
    public ResponseEntity<RestaurantResponseDTO> updateDeliveryFee(@PathVariable Integer id, @PathVariable double newFee) {
        return ResponseEntity.ok(restaurantService.updateDeliveryFee(id, newFee));
    }

    //List all MenuItems for the restaurant
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuItemResponseDTO>> getMenuForRestaurant(@PathVariable Integer id) {
        return ResponseEntity.ok(restaurantService.getMenuForRestaurant(id));
    }

    //List all ComboMeals for the restaurant
    @GetMapping("/{id}/combos")
    public ResponseEntity<List<ComboMealResponseDTO>> getCombosForRestaurant(@PathVariable Integer id) {
        return ResponseEntity.ok(restaurantService.getCombosForRestaurant(id));
    }

    //Add new MenuItem
    @PostMapping("/{id}/menu")
    public ResponseEntity<MenuItemResponseDTO> addMenuItem(@PathVariable Integer id, @RequestBody MenuItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.addMenuItem(id, dto));
    }

    //Mark item out of stock
    @PutMapping("/menu/{itemId}/available")
    public ResponseEntity<MenuItemResponseDTO> setMenuItemAvailability(@PathVariable Integer itemId, @RequestParam boolean status) {
        return ResponseEntity.ok(restaurantService.setMenuItemAvailability(itemId, status));
    }

    //Create a new ComboMeal
    @PostMapping("/{id}/combos")
    public ResponseEntity<ComboMealResponseDTO> createComboMeal(@PathVariable Integer id, @RequestBody ComboMealRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createComboMeal(id, dto));
    }

    //Increase all menu prices
    @PutMapping("/{id}/bulk-price-increase")
    public ResponseEntity<List<MenuItemResponseDTO>> bulkUpdateMenuItemPrices(@PathVariable Integer id, @RequestParam double percentage) {
        return ResponseEntity.ok(restaurantService.bulkUpdateMenuItemPrices(id, percentage));
    }

}
