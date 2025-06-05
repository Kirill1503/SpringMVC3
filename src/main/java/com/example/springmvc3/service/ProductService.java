package com.example.springmvc3.service;

import com.example.springmvc3.model.Product;
import com.example.springmvc3.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapperService objectMapperService;

    public ProductService(ProductRepository productRepository, ObjectMapperService objectMapperService) {
        this.productRepository = productRepository;
        this.objectMapperService = objectMapperService;
    }

    public String findAll() throws JsonProcessingException {
        return objectMapperService.toJson(productRepository.findAll());
    }

    public String findById(long id) throws JsonProcessingException {
        return objectMapperService.toJson(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с id " + id + " не найден")));
    }

    public String save(Product product) throws JsonProcessingException {
        return objectMapperService.toJson(productRepository.save(product));
    }

    public String update(long id, Product product) throws JsonProcessingException {
        Product exist = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Продукт с id " + id + " не найден"));
        exist.setName(product.getName());
        exist.setPrice(product.getPrice());
        exist.setDescription(product.getDescription());
        exist.setQuantityInStock(product.getQuantityInStock());
        return objectMapperService.toJson(productRepository.save(exist));
    }

    public void delete(long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Продукт с id " + id + " не найден");
        }
        productRepository.deleteById(id);
    }
}
