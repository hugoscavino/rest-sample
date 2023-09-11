package com.candidate.test.product.service;

import com.candidate.test.product.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Service Service Test")
public class ProductServiceTest extends AbstractServiceTest{
    @Autowired
    private ProductService service;
    @Test
    void crudProduct() {
        Long WAREHOUSE_ID = 100L;

        ProductDTO dto = new ProductDTO(0L,"PNAME-123445", "PDESCRIPTION", false, WAREHOUSE_ID);
        Long id = service.save(dto);
        assertTrue(id > 0);

        Optional<ProductDTO> foundDto = service.find(id);
        assertTrue(foundDto.isPresent());

        ProductDTO deletedDto = service.delete(99L);
        assertEquals(deletedDto.getId(), ProductService.BAD_PRODUCT_ID);

    }

    @Test
    void findByWarehouseId() {
        Long WAREHOUSE_ID = 1L;

        List<ProductDTO> productsByWareHouse = service.findByWarehouseId(WAREHOUSE_ID);
        assertTrue(!productsByWareHouse.isEmpty());
        productsByWareHouse.forEach(p -> {
            System.out.println("p id name warehouseId " + p.getId() + " " + p.getName() + " " + p.getWarehouseId());
            assertEquals( WAREHOUSE_ID, p.getWarehouseId(), "WAREHOUSE ID WAS " + p.getWarehouseId());
        });

    }

    @Test
    void updateProductName() {
        Long WAREHOUSE_ID = 1L;

        String originalName = "PNAME1000";
        ProductDTO dto = new ProductDTO(0L,originalName, "PDESCRIPTION", false, WAREHOUSE_ID);
        Long id = service.save(dto);
        assertTrue(id > 0);

        Optional<ProductDTO> foundDto = service.find(id);
        assertNotNull(foundDto.get());
        Long newId = foundDto.get().getId();
        String UPDATE_NAME_TO = "PNAME2000";
        service.updateName(id, UPDATE_NAME_TO);

        Optional<ProductDTO> updatedDto = service.find(id);
        String ACTUAL_NAME = updatedDto.get().getName();
        assertEquals(UPDATE_NAME_TO, ACTUAL_NAME);

    }

    @Test
    void findProduct() {
        final String LIKE_CRITERIA = "PNAME";
        List<ProductDTO> products = service.findByNameLike(LIKE_CRITERIA);
        assertTrue(!products.isEmpty());
        System.out.println("Found " + products.size() + " records");
        products.forEach(p -> {
            System.out.println("LIKE_CRITERIA " + LIKE_CRITERIA + " is like " + p.getName());
            assertTrue(p.getName().contains(LIKE_CRITERIA));
        });

    }
}
