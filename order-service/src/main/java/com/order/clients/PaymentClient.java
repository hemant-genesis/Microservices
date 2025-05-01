package com.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.order.dto.requests.PaymentRequest;
import com.order.dto.responses.PaymentResponse;

@FeignClient(name = "${app.clients.payment-service.name}", url = "${app.clients.payment-service.url}")
public interface PaymentClient {

    @PostMapping("/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest request);
}