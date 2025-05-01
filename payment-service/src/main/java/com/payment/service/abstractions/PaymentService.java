package com.payment.service.abstractions;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse getPaymentByOrderId(String orderId);
}
