package com.order.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.requests.OrderRequest;
import com.order.dto.responses.OrderResponse;
import com.order.service.abstractions.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "APIs for placing, retrieving, and canceling orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Place a new order")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order placed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/place-order")
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.placeOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an order by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") UUID id) {
        OrderResponse response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all orders for a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getAllOrdersOfUser(@PathVariable("userId") UUID userId) {
        List<OrderResponse> responses = orderService.getAllOrdersOfUser(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Cancel an order by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Order canceled successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable UUID id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
}
