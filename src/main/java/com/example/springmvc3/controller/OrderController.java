package com.example.springmvc3.controller;

import com.example.springmvc3.model.Order;
import com.example.springmvc3.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<String> getAllOrders() throws JsonProcessingException {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.save(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody Order order) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.update(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
