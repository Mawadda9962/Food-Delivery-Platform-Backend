package com.example.demo_TRA.Services;

import com.example.demo_TRA.DTOs.ResponseDTO.PaymentResponseDTO;
import com.example.demo_TRA.Entities.Order;
import com.example.demo_TRA.Entities.Payment;
import com.example.demo_TRA.Exceptions.InvalidOrderStateException;
import com.example.demo_TRA.Exceptions.ResourceNotFoundException;
import com.example.demo_TRA.Repositories.OrderRepository;
import com.example.demo_TRA.Repositories.PaymentRepository;
import com.example.demo_TRA.Repositories.ReviewRepository;
import com.example.demo_TRA.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderRepository orderRepository;


//processPayment (handle the transition of an order from a non-paid state to a secured, paid state)
    public PaymentResponseDTO processPayment(Integer orderId, String method) {
        Order order = orderRepository.findActiveById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Active Order not found with id: " + orderId));

        if ("PAID".equalsIgnoreCase(order.getStatus())) {
            throw new InvalidOrderStateException("Order has already been paid for.");
        }

        Payment payment = new Payment();
        payment.setPaymentMethod(method.toUpperCase());
        payment.setStatus("COMPLETED");
        payment.setAmount(order.getTotalAmount());
        payment.setTransactionRef(HelperUtils.generateCode("TXN", 10));
        payment.setProcessedAt(LocalDateTime.now());
        payment.setOrder(order);

        payment.setCreateDate(LocalDate.now());
        payment.setUpdateDate(LocalDateTime.now());
        payment.setIsActive(true);

        Payment savedPayment = paymentRepository.save(payment);

        order.setStatus("PAID");
        order.setUpdateDate(LocalDateTime.now());
        orderRepository.save(order);

        return PaymentResponseDTO.fromEntity(savedPayment);
    }

    //refund Payment
    public PaymentResponseDTO refundPayment(Integer orderId) {
        Payment payment = paymentRepository.findActivePaymentByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Active payment record not found for Order id: " + orderId));

        if ("REFUNDED".equalsIgnoreCase(payment.getStatus())) {
            throw new InvalidOrderStateException("This payment has already been refunded.");
        }

        payment.setStatus("REFUNDED");
        payment.setUpdateDate(LocalDateTime.now());
        Payment updatedPayment = paymentRepository.save(payment);

        Order order = payment.getOrder();
        if (order != null && order.getIsActive()) {
            order.setStatus("REFUNDED");
            order.setUpdateDate(LocalDateTime.now());
            orderRepository.save(order);
        }

        return PaymentResponseDTO.fromEntity(updatedPayment);
    }

}
