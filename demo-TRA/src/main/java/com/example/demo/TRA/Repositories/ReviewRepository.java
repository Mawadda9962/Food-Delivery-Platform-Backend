package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.id = :id AND r.isActive = true")
    Optional<Review> findActiveById(@Param("id") Integer id);

    @Query("SELECT r FROM Review r WHERE r.restaurant.id = :restaurantId AND r.isActive = true")
    List<Review> findByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT r FROM Review r WHERE r.deliveryDriver.id = :driverId AND r.isActive = true")
    List<Review> findByDeliveryDriverId(@Param("driverId") Integer driverId);
}
