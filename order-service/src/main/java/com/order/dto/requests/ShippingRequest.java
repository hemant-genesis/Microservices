package com.order.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShippingRequest {

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank(message = "City is required!")
    private String city;

    @NotBlank(message = "Postalcode is required!")
    private String postalCode;

    @NotBlank(message = "Country is required!")
    private String country;
}
