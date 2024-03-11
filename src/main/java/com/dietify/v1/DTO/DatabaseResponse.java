package com.dietify.v1.DTO;

import java.util.ArrayList;
import java.util.List;

public class DatabaseResponse {
    private List<Object> data = new ArrayList<>();

    public void setData(List<Object> data) {
        this.data = data;
    }

    public List<Object> getData() {
        return data;
    }
}
