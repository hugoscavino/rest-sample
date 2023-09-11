package com.candidate.test.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

/**
 *     A product has a numeric identifier,
 *     a name with at least 4 characters and no more than 80,
 *     an optional description and a
 *     flag indicating if it is in stock.
 */
@Entity
@Table(name = "product", schema = "product")
public class ProductEntity {

    /**
     * A product has a numeric identifier
     */
    @Id()
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * name with at least 4 characters and no more than 80
     */
    @NotBlank(message = "product name cannot be blank")
    @Length(min = 4, max = 80, message = "product name must be between 4-80 characters")
    private String name;

    /**
     * optional description
     */
    @Length(max = 1024, message = "product name must be between 0-1024 characters")
    private String description;

    /**
     * flag indicating if it is in stock, defaults to false
     */
    @NotNull(message = "isInStock cannot be null, defaults to False")
    @Column(name = "is_in_stock")
    private Boolean isInStock;

    /**
     * Join Table between Product and Warehouse. The fetch must be lazy
     * as we will use an outer join, and we do not want JPA to look for the
     * FK otherwise we get a duplicate row assertion at runtime
     */
    @JoinTable(
            name = "product_warehouse",
            schema = "product",
            joinColumns = @JoinColumn(
                    name = "product_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "warehouse_id",
                    referencedColumnName = "id"
            )
    )
    @OneToOne(fetch = FetchType.LAZY)
    private WarehouseEntity warehouse;

    public ProductEntity(){
    }
    public ProductEntity(Long id){
        this.id = id;
    }

    public ProductEntity(Long id, String name, String description, Boolean isInStock, WarehouseEntity warehouse) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isInStock = isInStock;
        this.warehouse = warehouse;
    }

    public ProductEntity(Long id, String name, String description, Boolean isInStock, Long warehouseId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isInStock = isInStock;
        this.warehouse = new WarehouseEntity(warehouseId);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getInStock() {
        return isInStock;
    }

    public void setInStock(Boolean inStock) {
        isInStock = inStock;
    }
    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity entity)) return false;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", warehouse='" + warehouse + '\'' +
                ", isInStock=" + isInStock +
                '}';
    }
}
