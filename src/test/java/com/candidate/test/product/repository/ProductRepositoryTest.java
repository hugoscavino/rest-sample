package com.candidate.test.product.repository;

import com.candidate.test.product.entity.ProductEntity;
import com.candidate.test.product.entity.WarehouseEntity;
import com.candidate.test.product.entity.builder.ProductEntityBuilder;
import com.candidate.test.product.entity.builder.WarehouseEntityBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.DERBY)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;


    @Test
    @DisplayName("Test Finding One Product by Id")
    void getByName()  {

        Long WAREHOUSE_ID = 256L;
        WarehouseEntity warehouseEntity = new WarehouseEntityBuilder().id(WAREHOUSE_ID).build();
        final String productName = "TEST_PRODUCT";
        ProductEntity entity = new ProductEntityBuilder()
                                .name(productName)
                                .description("Desc")
                                .isInStock(Boolean.FALSE)
                                .warehouse(warehouseEntity)
                                .build();

        assertNotNull(entity, "Did not Build a Row of type " + ProductEntity.class.getSimpleName());
        ProductEntity savedProductEntity = repository.save(entity);
        final Long savedProductEntityId = savedProductEntity.getId();
        System.out.println("savedProductEntity Product ID : " + savedProductEntityId);

        Iterable<ProductEntity> all = repository.findAll();
        AtomicInteger counter = new AtomicInteger();
        all.forEach((p) -> {
            System.out.println("ID : " + p.getId());
            counter.getAndIncrement();
        });

        // The three inserted and the new one
        assertEquals(4, counter.get());

        List<ProductEntity> listWithSameName = repository.findByName(productName);
        listWithSameName.forEach((p) -> {
            System.out.println("Found Product Name : " + p.getName());
            assertEquals(productName, p.getName());
        });

        List<ProductEntity> listWithLikeName = repository.findByNameLike(productName);
        listWithLikeName.forEach((p) -> {
            System.out.println("Found Product Names Like : " + p.getName());
            assertTrue(p.getName().contains(productName));
        });

        List<ProductEntity> allProductsByWarehouseId = repository.findAllProductsByWarehouseId(WAREHOUSE_ID);
        allProductsByWarehouseId.forEach((p) -> {
            System.out.println("Found Product Name : " + p.getName() + " with Warehouse Id " + p.getWarehouse().getId());
            assertEquals(p.getWarehouse().getId(), WAREHOUSE_ID);
        });
    }

    @Test
    @DisplayName("Test Update One Product")
    void updateProduct()  {
        final String productName = "TEST_PRODUCT_ONE";
        Long ORIG_WAREHOUSE_ID = 100L;

        WarehouseEntity origWarehouse = new WarehouseEntityBuilder().id(ORIG_WAREHOUSE_ID).build();

        ProductEntity entity = new ProductEntityBuilder()
                .name(productName)
                .description("Desc")
                .isInStock(Boolean.FALSE)
                .warehouse(origWarehouse)
                .build();

        ProductEntity savedEntity = repository.save(entity);
        assertNotNull(savedEntity, "Did not Build a Row of type " + ProductEntity.class.getSimpleName());

        Long savedId = savedEntity.getId();
        Optional<ProductEntity> productEntity = repository.findById(savedId);

        assertTrue(productEntity.isPresent(), "Did not find Entity by ID " + ProductEntity.class.getSimpleName());

        final ProductEntity foundEntity = productEntity.get();
        assertEquals(foundEntity.getId(), savedId, "Did not find Entity by ID found ID " + foundEntity.getId());

        final String newProductName = "TEST_PRODUCT_TWO";
        foundEntity.setName(newProductName);
        ProductEntity saved = repository.save(foundEntity);
        assertEquals(saved.getName(), newProductName, "Names did not match after the update " + foundEntity.getName());
        Long NEW_WAREHOUSE_ID = 200L;

        WarehouseEntity updatedWarehouse = new WarehouseEntityBuilder().id(NEW_WAREHOUSE_ID).build();
        foundEntity.setWarehouse(updatedWarehouse);
        saved = repository.save(foundEntity);
        assertEquals(saved.getWarehouse().getId(), NEW_WAREHOUSE_ID, "Warehouse not updated for Product " + foundEntity.getId() + " : " + foundEntity.getName());

    }

    @Test
    @DisplayName("Test Update One Product")
    void updateProductName()  {
        final String productName = "TEST_PRODUCT_ONE";
        ProductEntity entity = new ProductEntityBuilder()
                .name(productName)
                .description("Desc")
                .isInStock(Boolean.FALSE)
                .build();

        ProductEntity savedEntity = repository.save(entity);
        assertNotNull(savedEntity, "Did not Build a Row of type " + ProductEntity.class.getSimpleName());
        Long id = savedEntity.getId();
        final String newProductName = "TEST_PRODUCT_TWO";
        repository.updateName(id, newProductName);

        Optional<ProductEntity> optional = repository.findById(id);
        assertEquals(newProductName, optional.get().getName());

    }

}