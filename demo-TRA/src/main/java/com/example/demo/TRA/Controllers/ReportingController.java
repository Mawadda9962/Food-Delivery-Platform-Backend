package com.example.demo.TRA.Controllers;

import com.example.demo.TRA.DTOs.ResponseDTO.CustomerResponseDTO;
import com.example.demo.TRA.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("reports")
public class ReportingController {
    @Autowired
    ReviewService reviewService;

    // GET /api/reports/revenue/restaurant/{restaurantId}?date=YYYY-MM-DD
    @GetMapping("/revenue/restaurant/{restaurantId}")
    public ResponseEntity<Double> getRevenueForRestaurant(
            @PathVariable Integer restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(reviewService.getRevenueForRestaurantOnDate(restaurantId, date));
    }

    // GET /api/reports/orders/count/restaurant/{restaurantId}
    @GetMapping("/orders/count/restaurant/{restaurantId}")
    public ResponseEntity<Long> getTotalOrdersForRestaurant(
            @PathVariable Integer restaurantId) {

        return ResponseEntity.ok(reviewService.getTotalOrdersForRestaurant(restaurantId));
    }

    // GET /api/reports/customers/top-loyalty
    @GetMapping("/customers/top-loyalty")
    public ResponseEntity<List<CustomerResponseDTO>> getTopLoyaltyCustomers() {
        return ResponseEntity.ok(reviewService.getTopLoyaltyCustomers());
    }

    // GET /api/reports/drivers/leaderboard
    @GetMapping("/drivers/leaderboard")
    public ResponseEntity<List<Map<String, Object>>> getDriversLeaderboard() {
        return ResponseEntity.ok(reviewService.getDriversLeaderboard());
    }

    // GET /api/reports/platform/daily-summary?date=YYYY-MM-DD
    @GetMapping("/platform/daily-summary")
    public ResponseEntity<Map<String, Object>> getPlatformDailySummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(reviewService.getPlatformDailySummary(date));
    }

}
