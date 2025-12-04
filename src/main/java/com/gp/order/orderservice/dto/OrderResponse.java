package com.gp.order.orderservice.dto;

import com.gp.order.orderservice.model.Customer;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {

    private Long id;
    private List<String> product;
    private Integer quantity;
    private Double amount;

    private Long customerId;

    private String customerName;
    private String customerEmail;
    private Customer customer;
}
