package com.payment.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;
import com.payment.entity.Payment;
import com.payment.enums.PaymentStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final UUIDMapper uuidMapper;

    public PaymentResponse toReponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setTransactionId(uuidMapper.toString(payment.getId()));
        paymentResponse.setOrderId(uuidMapper.toString(payment.getOrderId()));
        paymentResponse.setMethod(payment.getMethod());
        paymentResponse.setStatus(payment.getStatus());
        paymentResponse.setCreatedAt(payment.getCreatedAt());
        return paymentResponse;
    }

    public Payment toEntity(PaymentRequest request, PaymentStatus paymentStatus){
        Payment payment = new Payment();
        payment.setOrderId(UUID.fromString(request.getOrderId()));
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getPaymentMethod());
        payment.setStatus(paymentStatus);
        return payment;
    }
}
