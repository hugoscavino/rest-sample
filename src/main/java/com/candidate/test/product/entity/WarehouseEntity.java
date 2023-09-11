package com.candidate.test.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;


/**
 * A warehouse has a numeric identifier,
 * a name with at least 10 characters and no more than 80 and
 * a zip code.
 */
@Entity
@Table(name = "warehouse", schema = "product")
public class WarehouseEntity {

    /**
     * warehouse has a numeric identifier,
     */
    @Id()
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * name with at least 10 characters and no more than 80
     */
    @NotBlank(message = "warehouse name cannot be blank")
    @Length(min = 10, max = 80, message = "warehouse name must be between 10-80 characters")
    private String name;

    /**
     * US 999999 zip code with min/max 5 char length, required
     */
    @NotBlank(message = "zipCode name cannot be blank")
    @Length(min = 5, max = 5, message = "zipCode name must be 5 characters")
    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne(mappedBy = "warehouse")
    private ProductEntity product;

    public WarehouseEntity(Long id) {
        this.id = id;
    }

    /**
     * No arg constructor for bean creation
     */
    public WarehouseEntity() {

    }
    public WarehouseEntity(Long id, String name, String zipCode) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, zipCode);
    }
}
