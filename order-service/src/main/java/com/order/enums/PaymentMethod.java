package com.order.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Available payment methods")
public enum PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    UPI,
    NET_BANKING,
    CASH_ON_DELIVERY
}
