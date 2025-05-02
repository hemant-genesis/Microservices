package com.order.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Shipping address information for the order")
public class ShippingRequest {

    @Schema(description = "Shipping address", example = "123 Main Street")
    @NotBlank(message = "Address is required!")
    private String address;

    @Schema(description = "City", example = "New York")
    @NotBlank(message = "City is required!")
    private String city;

    @Schema(description = "Postal code", example = "10001")
    @NotBlank(message = "Postal code is required!")
    private String postalCode;

    @Schema(description = "Country", example = "USA")
    @NotBlank(message = "Country is required!")
    private String country;
}
