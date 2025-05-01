package com.order.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.order.dto.responses.UserResponse;

@FeignClient(name = "${app.clients.user-service.name}", url = "${app.clients.user-service.url}")
public interface UserClient {

    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") String id);
}
