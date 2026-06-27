package com.example.demo.TRA.Controllers;

import com.example.demo.TRA.DTOs.RequestDTO.ReviewRequestDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.ReviewResponseDTO;
import com.example.demo.TRA.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {


    @Autowired
    ReviewService reviewService;

    // Submit restaurant review
    @PostMapping("/restaurant/{restaurantId}/customer/{customerId}")
    public ResponseEntity<ReviewResponseDTO> leaveRestaurantReview(@PathVariable Integer restaurantId, @PathVariable Integer customerId, @RequestBody ReviewRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.leaveRestaurantReview(customerId, restaurantId, dto.getRating(), dto.getComment()));
    }

    // Submit driver review → 201
    @PostMapping("/driver/{driverId}/customer/{customerId}")
    public ResponseEntity<ReviewResponseDTO> leaveDriverReview(@PathVariable Integer driverId, @PathVariable Integer customerId, @RequestBody ReviewRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.leaveDriverReview(customerId, driverId, dto.getRating(), dto.getComment()));
    }

    // Get all reviews for a restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewResponseDTO>> getRestaurantReviews(@PathVariable Integer restaurantId) {

        return ResponseEntity.ok(reviewService.getReviewsByRestaurant(restaurantId));
    }

    // Get all reviews for a driver
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<ReviewResponseDTO>> getDriverReviews(@PathVariable Integer driverId) {

        return ResponseEntity.ok(reviewService.getReviewsByDriver(driverId));
    }

    // Soft-delete a review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewId) {

        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    // Average rating for restaurant
    @GetMapping("/restaurant/{restaurantId}/average")
    public ResponseEntity<Double> getRestaurantAverage(@PathVariable Integer restaurantId){

        return ResponseEntity.ok(reviewService.getRestaurantAverageRating(restaurantId));
    }


    // Average rating for driver
    @GetMapping("/driver/{driverId}/average")
    public ResponseEntity<Double> getDriverAverage(@PathVariable Integer driverId){

        return ResponseEntity.ok(reviewService.getDriverAverageRating(driverId));
    }

    // Paginated reviews for restaurant
    @GetMapping("/restaurant/{restaurantId}/page")
    public ResponseEntity<List<ReviewResponseDTO>> getRestaurantReviewsPaginated(@PathVariable Integer restaurantId, @RequestParam int page, @RequestParam int size){

        return ResponseEntity.ok(reviewService.getRestaurantReviewsPaginated(restaurantId, page, size));
    }
}
