package com.candidate.test.product.entity.builder;

import com.candidate.test.product.entity.WarehouseEntity;
/**
 * Simple Builder for WarehouseEntityBuilder. Should be replaced with lombok annotation
 * once we confirm all the developers have it configured in their IDE's
 */
public class WarehouseEntityBuilder {
    private Long id;
    private String name;
    private String zipCode;

    public WarehouseEntityBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public WarehouseEntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public WarehouseEntityBuilder zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public WarehouseEntity build() {
        WarehouseEntity warehouseEntity = new WarehouseEntity(id);
        warehouseEntity.setName(name);
        warehouseEntity.setZipCode(zipCode);
        return warehouseEntity;
    }
}