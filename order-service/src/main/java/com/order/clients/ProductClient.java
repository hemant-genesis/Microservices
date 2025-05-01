package com.order.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.order.dto.responses.ProductResponse;

@FeignClient(name = "${app.clients.product-service.name}", url = "${app.clients.product-service.url}")
public interface ProductClient {

    @PostMapping("/ids")
    List<ProductResponse> getProductsByIdList(@RequestBody List<String> idList);

    @PutMapping("/{id}")
    ProductResponse updateProductQuatity(@PathVariable("id") String id,@RequestBody String quantity);
}
