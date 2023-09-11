package com.candidate.test.product.service;

import com.candidate.test.product.dto.WarehouseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Warehouse Service Test")
class WarehouseServiceTest extends AbstractServiceTest{

    @Autowired
    private WarehouseService service;

    @Test
    void crudWarehouse() {

        WarehouseDTO dto = new WarehouseDTO("WAREHOUSE-123445", "33544");
        Long id = service.save(dto);
        assertTrue(id > 0);

        Optional<WarehouseDTO> foundDto = service.find(id);
        assertNotNull(foundDto.get());

        Long foundId = service.delete(id);
        assertEquals(id, foundId);

    }
}