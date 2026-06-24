package com.example.demo_TRA.Services;

import com.example.demo_TRA.Repositories.PaymentRepository;
import com.example.demo_TRA.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ReviewRepository reviewRepository;

}
