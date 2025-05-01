package com.payment.service.abstractions;

import com.payment.dto.PaymentRequest;
import com.payment.entity.Payment;

public interface PaymentService {
    Payment processPayment(PaymentRequest request);
    Payment getPaymentByOrderId(String orderId);
}
