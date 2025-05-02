package com.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;
import com.payment.service.abstractions.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "APIs for payments users")
public class PaymentController {
    
    private final PaymentService paymentService;

    @PostMapping("/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }

    @Operation(summary = "Get a payments details by orderID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Payment found"),
        @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @GetMapping("/by-order/{orderId}")
    public PaymentResponse getPayment(@PathVariable String orderId) {
        return paymentService.getPaymentByOrderId(orderId);
    }
}
