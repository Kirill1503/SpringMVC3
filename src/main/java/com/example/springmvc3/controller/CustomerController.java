package com.example.springmvc3.controller;

import com.example.springmvc3.model.Customer;
import com.example.springmvc3.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<String> getAllCustomers() throws JsonProcessingException {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCustomer(@PathVariable Long id) throws JsonProcessingException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) throws JsonProcessingException {
        return ResponseEntity.ok(customerService.save(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) throws JsonProcessingException {
        return ResponseEntity.ok(customerService.update(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
