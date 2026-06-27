package com.example.demo.TRA.Services;

import com.example.demo.TRA.DTOs.ResponseDTO.PaymentResponseDTO;
import com.example.demo.TRA.Entities.Order;
import com.example.demo.TRA.Entities.Payment;
import com.example.demo.TRA.Exceptions.InvalidOrderStateException;
import com.example.demo.TRA.Exceptions.ResourceNotFoundException;
import com.example.demo.TRA.Repositories.OrderRepository;
import com.example.demo.TRA.Repositories.PaymentRepository;
import com.example.demo.TRA.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Complete Payment
    public PaymentResponseDTO completePayment(Integer paymentId) {
        Payment payment = paymentRepository.findActiveById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));

        if ("COMPLETED".equalsIgnoreCase(payment.getStatus())) {
            throw new InvalidOrderStateException("Payment is already completed.");
        }

        payment.setStatus("COMPLETED");
        payment.setProcessedAt(LocalDateTime.now());
        payment.setUpdateDate(LocalDateTime.now());

        return PaymentResponseDTO.fromEntity(paymentRepository.save(payment));
    }

    // Get Payment By OrderId
    public PaymentResponseDTO getPaymentByOrderId(Integer orderId) {
        Payment payment = paymentRepository.findActivePaymentByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found for order id: " + orderId));

        return PaymentResponseDTO.fromEntity(payment);
    }

    //Extended Use-Case Endpoints
    // Get Payments With Filters
    public List<PaymentResponseDTO> getPayments(String method, String status, LocalDate from, LocalDate to) {
        List<Payment> payments = paymentRepository.findAll();
        List<Payment> filteredPayments = new ArrayList<>();

        for(Payment payment : payments){
            // soft delete check
            if(!payment.getIsActive()){
                continue;
            }

            boolean match = true;
            if(method != null && !payment.getPaymentMethod().equalsIgnoreCase(method)){
                match = false;
            }

            if(status != null && !payment.getStatus().equalsIgnoreCase(status)) {
                match = false;
            }

            if(from != null && payment.getProcessedAt() != null && payment.getProcessedAt().toLocalDate().isBefore(from)){
                match = false;
            }

            if(to != null && payment.getProcessedAt() != null && payment.getProcessedAt().toLocalDate().isAfter(to)){
                match = false;
            }

            if(match){
                filteredPayments.add(payment);
            }
        }

        return PaymentResponseDTO.fromEntity(filteredPayments);
    }

    // Analytics By Payment Method
    public Map<String,Object> getPaymentAnalyticsByMethod(){
        List<Payment> payments = paymentRepository.findAll();
        Map<String,Double> totals = new HashMap<>();

        for(Payment payment : payments) {
            if (!payment.getIsActive()) {
                continue;
            }
            if (!"COMPLETED".equalsIgnoreCase(payment.getStatus())) {
                continue;
            }
            String method = payment.getPaymentMethod();
            Double amount = totals.getOrDefault(method, 0.0);
            totals.put(method, amount + payment.getAmount());
        }

        Map<String,Object> result = new HashMap<>();
        result.put("totalProcessedByMethod", totals);
        return result;
    }
}
