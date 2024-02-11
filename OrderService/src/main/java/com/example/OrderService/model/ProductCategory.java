package com.example.OrderService.model;

public enum ProductCategory {

    ELECTRONICS("ELECTRONICS"),
    FASHION("FASHION"),
    BOOKS("BOOKS");

    private String value;

    private ProductCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}