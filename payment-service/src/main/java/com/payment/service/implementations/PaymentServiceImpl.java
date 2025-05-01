package com.payment.service.implementations;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;
import com.payment.entity.Payment;
import com.payment.enums.PaymentStatus;
import com.payment.repository.PaymentRepository;
import com.payment.service.abstractions.PaymentService;
import com.payment.utils.Utils;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Payment payment = Utils.toEntity(request, PaymentStatus.SUCCESS);
        Payment savedPayment = paymentRepository.save(payment);
        return Utils.toReponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        Payment savedPayment = paymentRepository.findByOrderId(UUID.fromString(orderId));
        return Utils.toReponse(savedPayment);
    }
}
