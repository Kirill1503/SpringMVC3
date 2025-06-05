package com.example.springmvc3.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperService {
    private final ObjectMapper objectMapper;

    public ObjectMapperService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
