package com.payment.service.implementations;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.payment.dto.PaymentRequest;
import com.payment.entity.Payment;
import com.payment.enums.PaymentStatus;
import com.payment.repository.PaymentRepository;
import com.payment.service.abstractions.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment processPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(UUID.fromString(request.getOrderId()));
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.COMPLETED); // Or set logic for PENDING, etc.

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(UUID.fromString(orderId));
    }
}
