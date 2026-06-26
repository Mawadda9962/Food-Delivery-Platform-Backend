package com.example.demo.TRA.Services;

import com.example.demo.TRA.DTOs.ResponseDTO.ReviewResponseDTO;
import com.example.demo.TRA.Entities.Customer;
import com.example.demo.TRA.Entities.DeliveryDriver;
import com.example.demo.TRA.Entities.Restaurant;
import com.example.demo.TRA.Entities.Review;
import com.example.demo.TRA.Exceptions.ResourceNotFoundException;
import com.example.demo.TRA.Repositories.CustomerRepository;
import com.example.demo.TRA.Repositories.DeliveryDriverRepository;
import com.example.demo.TRA.Repositories.RestaurantRepository;
import com.example.demo.TRA.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    DeliveryDriverRepository deliveryDriverRepository;

    public ReviewResponseDTO leaveRestaurantReview(Integer customerId, Integer restaurantId, int rating, String comment) {
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Active Customer not found with id: " + customerId));

        Restaurant restaurant = restaurantRepository.findActiveById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Active Restaurant not found with id: " + restaurantId));

        Review review = new Review();
        review.setTargetType("RESTAURANT");
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setDeliveryDriver(null);

        review.setCreateDate(LocalDate.now());
        review.setUpdateDate(LocalDateTime.now());
        review.setIsActive(true);

        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDTO.fromEntity(savedReview);
    }


    public ReviewResponseDTO leaveDriverReview(Integer customerId, Integer driverId, int rating, String comment) {
        Customer customer = customerRepository.findActiveById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Active Customer not found with id: " + customerId));

        DeliveryDriver driver = deliveryDriverRepository.findActiveById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Active Delivery Driver not found with id: " + driverId));

        Review review = new Review();
        review.setTargetType("DRIVER");
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());
        review.setCustomer(customer);
        review.setRestaurant(null);
        review.setDeliveryDriver(driver);

        review.setCreateDate(LocalDate.now());
        review.setUpdateDate(LocalDateTime.now());
        review.setIsActive(true);

        Review savedReview = reviewRepository.save(review);
        return ReviewResponseDTO.fromEntity(savedReview);
    }

    // Get Reviews By Restaurant
    public List<ReviewResponseDTO> getReviewsByRestaurant(Integer restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
        return ReviewResponseDTO.fromEntity(reviews);
    }


    // Get Reviews By Driver
    public List<ReviewResponseDTO> getReviewsByDriver(Integer driverId) {
        List<Review> reviews = reviewRepository.findByDeliveryDriverId(driverId);
        return ReviewResponseDTO.fromEntity(reviews);
    }

    // Soft-delete Review
    public void deleteReview(Integer reviewId) {
        Review review = reviewRepository.findActiveById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        review.setIsActive(false);
        review.setUpdateDate(LocalDateTime.now());
        reviewRepository.save(review);
    }


}
