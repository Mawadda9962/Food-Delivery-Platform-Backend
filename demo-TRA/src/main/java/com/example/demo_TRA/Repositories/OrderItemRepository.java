package com.example.demo_TRA.Repositories;

import com.example.demo_TRA.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId AND oi.isActive = true")
    List<OrderItem> findByOrderId(@Param("orderId") Integer orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.id = :id AND oi.isActive = true")
    List<OrderItem> findActiveById(@Param("id") Integer id);
}
