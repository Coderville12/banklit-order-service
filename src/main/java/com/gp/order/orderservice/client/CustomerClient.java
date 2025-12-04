package com.gp.order.orderservice.client;

import com.gp.order.orderservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${customer.service.url}")
public interface CustomerClient {

    @GetMapping("/api/customers/{id}")
    Customer getCustomer(@PathVariable("id") Long id);
}
