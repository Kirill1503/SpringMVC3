package com.example.springmvc3.service;

import com.example.springmvc3.model.Product;
import com.example.springmvc3.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ObjectMapperService objectMapperService;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        objectMapperService = mock(ObjectMapperService.class);
        productService = new ProductService(productRepository, objectMapperService);
    }

    @Test
    void testFindAll() throws JsonProcessingException {
        List<Product> products = List.of(new Product());
        when(productRepository.findAll()).thenReturn(products);
        when(objectMapperService.toJson(products)).thenReturn("json");

        assertEquals("json", productService.findAll());
    }

    @Test
    void testFindById() throws JsonProcessingException {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(objectMapperService.toJson(product)).thenReturn("json");

        assertEquals("json", productService.findById(1L));
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.findById(1L));
    }

    @Test
    void testSave() throws JsonProcessingException {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);
        when(objectMapperService.toJson(product)).thenReturn("json");

        assertEquals("json", productService.save(product));
    }

    @Test
    void testUpdate() throws JsonProcessingException {
        Product existing = new Product();
        existing.setName("Old");

        Product updated = new Product();
        updated.setName("New");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);
        when(objectMapperService.toJson(existing)).thenReturn("json");

        assertEquals("json", productService.update(1L, updated));
        assertEquals("New", existing.getName());
    }
}