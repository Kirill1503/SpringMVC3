package com.example.springmvc3.service;

import com.example.springmvc3.model.Order;
import com.example.springmvc3.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ObjectMapperService objectMapperService;

    public OrderService(OrderRepository orderRepository, ObjectMapperService objectMapperService) {
        this.orderRepository = orderRepository;
        this.objectMapperService = objectMapperService;
    }


    public String findAll() throws JsonProcessingException {
        return objectMapperService.toJson(orderRepository.findAll());
    }

    public String findById(long id) throws JsonProcessingException {
        return objectMapperService.toJson(orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с id " + id + " не найден")));
    }

    public String save(Order order) throws JsonProcessingException {
        return objectMapperService.toJson(orderRepository.save(order));
    }

    public String update(long id, Order order) throws JsonProcessingException {
        Order exist = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с id " + id + " не найден"));
        exist.setTotalPrice(order.getTotalPrice());
        exist.setOrderDate(order.getOrderDate());
        exist.setOrderStatus(order.getOrderStatus());
        exist.setProducts(order.getProducts());
        exist.setShippingAddress(order.getShippingAddress());
        exist.setCustomer(order.getCustomer());
        return objectMapperService.toJson(orderRepository.save(exist));
    }

    public void delete(long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Заказ с id " + id + " не найден");
        }
        orderRepository.deleteById(id);
    }
}
