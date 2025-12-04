package com.gp.order.orderservice.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    @NotEmpty(message = "Products cannot be empty")
    private List<String> product;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
