package com.example.demo.TRA.Controllers;

import com.example.demo.TRA.DTOs.ResponseDTO.PaymentResponseDTO;
import com.example.demo.TRA.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    // Create payment record
    @PostMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> processPayment(@PathVariable Integer orderId, @RequestParam String method) {

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(orderId, method));
    }

    // Mark payment as successful
    @PutMapping("/{paymentId}/complete")
    public ResponseEntity<PaymentResponseDTO> completePayment(@PathVariable Integer paymentId) {

        return ResponseEntity.ok(paymentService.completePayment(paymentId));
    }

    // Refund payment
    @PutMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(@PathVariable Integer paymentId) {

        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

    // View payment info for an order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrderId(@PathVariable Integer orderId) {

        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }

    // Filterable Payment List
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getPayments(
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        return ResponseEntity.ok(paymentService.getPayments(method, status, from, to));
    }

    // Payment Analytics By Method
    @GetMapping("/analytics/by-method")
    public ResponseEntity<Map<String,Object>> getPaymentAnalyticsByMethod() {
        return ResponseEntity.ok(paymentService.getPaymentAnalyticsByMethod());
    }

}
