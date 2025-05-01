package com.order.service.abstractions;

import java.util.List;
import java.util.UUID;

import com.order.dto.requests.OrderRequest;
import com.order.dto.responses.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
    OrderResponse getOrderById(UUID id);
    List<OrderResponse> getAllOrdersOfUser(UUID userId);
    OrderResponse updateOrder(UUID id, OrderRequest request);
    OrderResponse cancelOrder(UUID id);
}
