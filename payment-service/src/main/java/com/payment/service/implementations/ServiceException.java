package com.payment.service.implementations;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}