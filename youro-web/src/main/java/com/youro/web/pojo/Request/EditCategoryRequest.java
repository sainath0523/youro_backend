package com.youro.web.pojo.Request;

import jakarta.validation.constraints.NotBlank;

public class EditCategoryRequest {
    //@NotBlank(message = "Category name cannot be blank")
    public String name;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}