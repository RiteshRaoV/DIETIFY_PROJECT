package com.dietify.v1.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatabaseResponse {
    private List<Object> data = new ArrayList<>();

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<Object> getData() {
        return data;
    }

    // Use Jackson ObjectMapper for better JSON handling
    public String getJsonData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception as needed
            return "Error converting to JSON";
        }
    }
}
