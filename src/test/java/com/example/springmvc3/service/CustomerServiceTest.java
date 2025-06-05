package com.example.springmvc3.service;

import com.example.springmvc3.model.Customer;
import com.example.springmvc3.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private ObjectMapperService objectMapperService;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        objectMapperService = mock(ObjectMapperService.class);
        customerService = new CustomerService(customerRepository, objectMapperService);
    }

    @Test
    void testFindAll() throws JsonProcessingException {
        List<Customer> customers = List.of(new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        when(objectMapperService.toJson(customers)).thenReturn("json");

        String result = customerService.findAll();
        assertEquals("json", result);
    }

    @Test
    void testFindById() throws JsonProcessingException {
        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(objectMapperService.toJson(customer)).thenReturn("json");

        String result = customerService.findById(1L);
        assertEquals("json", result);
    }

    @Test
    void testFindByIdNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.findById(1L));
    }

    @Test
    void testSave() throws JsonProcessingException {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);
        when(objectMapperService.toJson(customer)).thenReturn("json");

        assertEquals("json", customerService.save(customer));
    }

    @Test
    void testUpdate() throws JsonProcessingException {
        Customer existing = new Customer();
        existing.setFirstName("Old");

        Customer updated = new Customer();
        updated.setFirstName("New");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.save(existing)).thenReturn(existing);
        when(objectMapperService.toJson(existing)).thenReturn("json");

        assertEquals("json", customerService.update(1L, updated));
        assertEquals("New", existing.getFirstName());
    }
}