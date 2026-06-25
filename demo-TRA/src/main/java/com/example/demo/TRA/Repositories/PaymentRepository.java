package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.id = :id AND p.isActive = true")
    Optional<Payment> findActiveById(@Param("id") Integer id);

    @Query("SELECT p FROM Payment p WHERE p.order.id = :orderId AND p.isActive = true")
    Optional<Payment> findActivePaymentByOrderId(@Param("orderId") Integer orderId);
}
