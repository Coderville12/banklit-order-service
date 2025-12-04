package com.gp.order.orderservice.model;

import com.gp.order.orderservice.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private List<Order> orders;
}
