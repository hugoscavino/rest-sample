package com.candidate.test.product.entity.builder;

import com.candidate.test.product.entity.ProductEntity;
import com.candidate.test.product.entity.WarehouseEntity;

/**
 * Simple Builder for ProductEntityBuilder. Should be replaced with lombok annotation
 * once we confirm all the developers have it configured in their IDE's
 */
public class ProductEntityBuilder {
    private Long id;
    private String name;
    private String description;
    private Boolean isInStock;
    private WarehouseEntity warehouse;

    public ProductEntityBuilder(){
        this.isInStock = false; // Defaults to false
    }

    public ProductEntityBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ProductEntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ProductEntityBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ProductEntityBuilder isInStock(Boolean isInStock) {
        this.isInStock = isInStock;
        return this;
    }
    public ProductEntityBuilder warehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
        return this;
    }
    public ProductEntity build() {
        return new ProductEntity(id, name, description, isInStock,warehouse);
    }
}