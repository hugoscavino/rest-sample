package com.candidate.test.product.entity;

import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

/**
 * Primary Key Table for the product warehouse relationship. The relationship is One to One
 * A product can only be in one warehouse at a time. There is a constraint on this table to
 * maintain the single warehouse concept. The constraint can be removed if our assumptions
 * change
 *
 */
public class ProductWarehouseId implements Serializable {

    @Id()
    private Long productId;

    @Id()
    private Long warehouseId;

    public ProductWarehouseId(){

    }
    public ProductWarehouseId(Long productId, Long warehouseId){
        this.productId = productId;
        this.warehouseId = warehouseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductWarehouseId that)) return false;
        return Objects.equals(productId, that.productId) && Objects.equals(warehouseId, that.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, warehouseId);
    }

    @Override
    public String toString() {
        return "ProductWarehouseId{" +
                "productId=" + productId +
                ", warehouseId=" + warehouseId +
                '}';
    }
}
