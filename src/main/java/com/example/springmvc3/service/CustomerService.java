package com.example.springmvc3.service;

import com.example.springmvc3.model.Customer;
import com.example.springmvc3.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ObjectMapperService objectMapperService;

    public CustomerService(CustomerRepository customerRepository, ObjectMapperService objectMapperService) {
        this.customerRepository = customerRepository;
        this.objectMapperService = objectMapperService;
    }


    public String findAll() throws JsonProcessingException {
        return objectMapperService.toJson(customerRepository.findAll());
    }

    public String findById(long id) throws JsonProcessingException {
        return objectMapperService.toJson(customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Клиент с id " + id + " не найден")));
    }

    public String save(Customer customer) throws JsonProcessingException {
        return objectMapperService.toJson(customerRepository.save(customer));
    }

    public String update(long id, Customer customer) throws JsonProcessingException {
        Customer exist = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Клиент с id " + id + " не найден"));
        exist.setFirstName(customer.getFirstName());
        exist.setLastName(customer.getLastName());
        exist.setEmail(customer.getEmail());
        exist.setContactNumber(customer.getContactNumber());
        return objectMapperService.toJson(customerRepository.save(exist));
    }

    public void delete(long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Клиент с id " + id + " не найден");
        }
        customerRepository.deleteById(id);
    }
}
