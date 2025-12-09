package com.gp.order.orderservice.entity;

import com.gp.order.orderservice.model.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product")
    private String  product;

    private Integer quantity;

    private Double amount;

    // WRONG DESIGN — for reproducing problems
    // Customer does not exist in this microservice
    @Transient
    private Customer customer;
    // Correct DB fields
    @Column(name = "customer_id")
    private Long customerId;
    // Correct audit fields
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;

    @Version
    private Long version;
}
