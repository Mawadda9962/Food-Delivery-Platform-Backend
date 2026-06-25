package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query("SELECT o FROM Order o " + "WHERE o.customer.id = :customerId AND o.isActive = true")
    List<Order> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT o FROM Order o " + "WHERE o.restaurant.id = :restaurantId AND o.status = :status AND o.isActive = true")
    List<Order> findByRestaurantIdAndStatus(@Param("restaurantId") Integer restaurantId, @Param("status") String status);

    @Query("SELECT o FROM Order o " + "WHERE o.orderDate BETWEEN :start AND :end AND o.isActive = true")
    List<Order> findByOrderDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COUNT(o) FROM Order o " + "WHERE o.restaurant.id = :restaurantId AND o.status = 'DELIVERED' AND o.isActive = true")
    long countCompletedOrdersForRestaurant(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o " + "WHERE o.status = 'DELIVERED' AND o.orderDate = :date AND o.isActive = true")
    Double sumDeliveredRevenueForDate(@Param("date") LocalDate date);

    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.isActive = true")
    Optional<Order> findActiveById(@Param("id") Integer id);
}
