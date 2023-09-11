package com.candidate.test.product.repository;

import com.candidate.test.product.entity.WarehouseEntity;
import com.candidate.test.product.entity.builder.WarehouseEntityBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.DERBY)
class WarehouseRepositoryTest {

    @Autowired
    private WarehouseRepository repository;
    @Test
    @DisplayName("Test Finding One Warehouse by Id")
    void findByName() {

        final String warehouseName= "WH12345678";
        final String zipCode= "33544";
        Long WH_ID = 200L;
        WarehouseEntity entity = new WarehouseEntityBuilder()
                .id(WH_ID)
                .name(warehouseName)
                .zipCode(zipCode)
                .build();

        assertNotNull(entity, "Did not Build a Row of type " + WarehouseEntity.class.getSimpleName());
        repository.save(entity);


        Iterable<WarehouseEntity> all = repository.findAll();
        all.forEach((p) -> {
            System.out.println("ID : " + p.getId());
        });

        // The three inserted and the new one
        assertTrue(repository.count() > 0);

        List<WarehouseEntity> listWithSameName = repository.findByName(warehouseName);
        listWithSameName.forEach((p) -> {
            System.out.println("Found Warehouse Name : " + p.getName());
            assertEquals(p.getName(), warehouseName);
        });
    }
}