package com.candidate.test.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * Simple DTO representation of a Warehouse
 *<br>
 * A warehouse has a numeric identifier,
 * a name with at least 10 characters and no more than 80 and a
 * zip code.
 *<br>
 * NOTE: We are deferring Lombok because we are not sure all the developers have the JAR configured.
 */
public class WarehouseDTO {


    /**
     * A warehouse has a numeric identifier
     */
    @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * a name with at least 10 characters
     */
    @Schema(name = "name", example = "WAREHOUSE1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * Simple US Zip code in the 99999 format
     */
    @Schema(name = "zipCode", example = "33544", requiredMode = Schema.RequiredMode.REQUIRED)
    private String zipCode;

    /**
     * None of the attributes are defaulted.
     */
    public WarehouseDTO() {
    }

    /**
     * None of the attributes are defaulted.
     */
    public WarehouseDTO(Long id) {
        this.id = id;
    }

    public WarehouseDTO(Long id, String name, String zipCode) {
        this.id = id;
        this.name = name;
        this.zipCode = zipCode;
    }

    public WarehouseDTO(String name, String zipCode) {
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
        if (!(o instanceof WarehouseDTO dto)) return false;
        return Objects.equals(id, dto.id) && Objects.equals(name, dto.name) && Objects.equals(zipCode, dto.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, zipCode);
    }

    @Override
    public String toString() {
        return "WarehouseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}