package com.payment.utils;

import java.util.UUID;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;
import com.payment.entity.Payment;
import com.payment.enums.PaymentStatus;

public class Utils {
    public static String toString(UUID uuid){
        return uuid.toString();
    }

    public static UUID toUUID(String uuidString){
        return UUID.fromString(uuidString);
    }

    public static PaymentResponse toReponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setTransactionId(Utils.toString(payment.getId()));
        paymentResponse.setOrderId(Utils.toString(payment.getOrderId()));
        paymentResponse.setMethod(payment.getMethod());
        paymentResponse.setStatus(payment.getStatus());
        paymentResponse.setCreatedAt(payment.getCreatedAt());
        return paymentResponse;
    }

    public static Payment toEntity(PaymentRequest request, PaymentStatus paymentStatus){
        Payment payment = new Payment();
        payment.setOrderId(UUID.fromString(request.getOrderId()));
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getPaymentMethod());
        payment.setStatus(paymentStatus);
        return payment;
    }
}
