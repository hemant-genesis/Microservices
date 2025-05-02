package com.payment.service.implementations;

import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.payment.dto.PaymentRequest;
import com.payment.dto.PaymentResponse;
import com.payment.entity.Payment;
import com.payment.enums.PaymentStatus;
import com.payment.exceptions.DatabaseException;
import com.payment.exceptions.ResourceNotFoundException;
import com.payment.mappers.PaymentMapper;
import com.payment.mappers.UUIDMapper;
import com.payment.repository.PaymentRepository;
import com.payment.service.abstractions.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UUIDMapper uuidMapper;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment for order ID: {}", request.getOrderId());
        try {
            Payment payment = paymentMapper.toEntity(request, PaymentStatus.SUCCESS);
            Payment savedPayment = paymentRepository.save(payment);
            log.info("Payment processed successfully for order ID: {}", request.getOrderId());
            return paymentMapper.toReponse(savedPayment);
        } catch (DataAccessException e) {
            log.error("Database error during payment processing", e);
            throw new DatabaseException("Failed to process payment. Please try again later.");
        } catch (Exception e) {
            log.error("Unexpected error during payment processing", e);
            throw new ServiceException("Unexpected error occurred during payment processing.");
        }
    }

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        log.info("Fetching payment for order ID: {}", orderId);
        try {
            UUID orderUUID = uuidMapper.toUUID(orderId);
            Payment savedPayment = paymentRepository.findByOrderId(orderUUID);
            if (savedPayment == null) {
                log.warn("Payment not found for order ID: {}", orderId);
                throw new ResourceNotFoundException("Payment not found for order ID: " + orderId);
            }
            return paymentMapper.toReponse(savedPayment);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format for order ID: {}", orderId, e);
            throw new InvalidInputException("Invalid order ID format: " + orderId);
        } catch (DataAccessException e) {
            log.error("Database error while fetching payment", e);
            throw new DatabaseException("Failed to retrieve payment. Please try again later.");
        }
    }
}
