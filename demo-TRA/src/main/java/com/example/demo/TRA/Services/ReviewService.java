package com.example.demo.TRA.Services;

import com.example.demo.TRA.DTOs.ResponseDTO.CustomerResponseDTO;
import com.example.demo.TRA.DTOs.ResponseDTO.ReviewResponseDTO;
import com.example.demo.TRA.Entities.Customer;
import com.example.demo.TRA.Entities.DeliveryDriver;
import com.example.demo.TRA.Entities.Restaurant;
import com.example.demo.TRA.Entities.Review;
import com.example.demo.TRA.Exceptions.ResourceNotFoundException;
import com.example.demo.TRA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DeliveryRepository deliveryRepository;


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



    //For Reporting
    // Revenue for a restaurant on a specific day
    public Double getRevenueForRestaurantOnDate(Integer restaurantId, LocalDate date) {
        return orderRepository.sumDeliveredRevenueForRestaurantOnDate(restaurantId, date);
    }

    // Total lifetime orders for a restaurant
    public Long getTotalOrdersForRestaurant(Integer restaurantId) {
        return orderRepository.countCompletedOrdersForRestaurant(restaurantId);
    }

    // Top 10 customers by loyalty points
    public List<CustomerResponseDTO> getTopLoyaltyCustomers() {
        return CustomerResponseDTO.fromEntity(
                customerRepository.findTop10ByLoyaltyPoints());
    }

    // Top drivers by completed deliveries
    public List<Map<String, Object>> getDriversLeaderboard() {
        return deliveryRepository.findDriversLeaderboard();
    }

    // Platform daily summary
    public Map<String, Object> getPlatformDailySummary(LocalDate date) {
        Long totalOrders = orderRepository.countOrdersForDate(date);
        Double totalRevenue = orderRepository.sumDeliveredRevenueForDate(date);
        Double totalDeliveryFees = orderRepository.sumDeliveryFeesForDate(date);

        Map<String, Object> summary = new HashMap<>();
        summary.put("date", date);
        summary.put("totalOrders", totalOrders);
        summary.put("totalRevenue", totalRevenue != null ? totalRevenue : 0.0);
        summary.put("totalDeliveryFees", totalDeliveryFees != null ? totalDeliveryFees : 0.0);

        return summary;
    }



    //Extended Use-Case Endpoints
    // Get Average Restaurant Rating
    public Double getRestaurantAverageRating(Integer restaurantId) {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);

        if(reviews.isEmpty()){
            return 0.0;
        }
        double total = 0;
        for(Review review : reviews){

            total += review.getRating();
        }

        return total / reviews.size();
    }


    // Get Average Driver Rating
    public Double getDriverAverageRating(Integer driverId) {
        List<Review> reviews = reviewRepository.findByDeliveryDriverId(driverId);

        if(reviews.isEmpty()){
            return 0.0;
        }

        double total = 0;

        for(Review review : reviews){
            total += review.getRating();
        }

        return total / reviews.size();
    }


    // Paginated Restaurant Reviews
    public List<ReviewResponseDTO> getRestaurantReviewsPaginated(Integer restaurantId, Integer page, Integer size){
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);

        if(page == null || size == null){
            return ReviewResponseDTO.fromEntity(reviews);
        }

        int start = page * size;
        int end = Math.min(start + size, reviews.size());

        if(start >= reviews.size()){
            return new ArrayList<>();
        }

        return ReviewResponseDTO.fromEntity(reviews.subList(start,end));
    }
}
