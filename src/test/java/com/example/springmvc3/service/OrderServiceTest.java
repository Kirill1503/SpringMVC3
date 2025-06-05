package com.example.springmvc3.service;

import com.example.springmvc3.model.Order;
import com.example.springmvc3.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private ObjectMapperService objectMapperService;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        objectMapperService = mock(ObjectMapperService.class);
        orderService = new OrderService(orderRepository, objectMapperService);
    }

    @Test
    void testFindAll() throws JsonProcessingException {
        List<Order> orders = List.of(new Order());
        when(orderRepository.findAll()).thenReturn(orders);
        when(objectMapperService.toJson(orders)).thenReturn("json");

        assertEquals("json", orderService.findAll());
    }

    @Test
    void testFindById() throws JsonProcessingException {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(objectMapperService.toJson(order)).thenReturn("json");

        assertEquals("json", orderService.findById(1L));
    }

    @Test
    void testFindByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.findById(1L));
    }

    @Test
    void testSave() throws JsonProcessingException {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);
        when(objectMapperService.toJson(order)).thenReturn("json");

        assertEquals("json", orderService.save(order));
    }

    @Test
    void testUpdate() throws JsonProcessingException {
        Order existing = new Order();
        Order updated = new Order();
        updated.setTotalPrice(BigDecimal.valueOf(150.0));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(orderRepository.save(existing)).thenReturn(existing);
        when(objectMapperService.toJson(existing)).thenReturn("json");

        assertEquals("json", orderService.update(1L, updated));
    }
}