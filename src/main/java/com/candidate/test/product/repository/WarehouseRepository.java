package com.candidate.test.product.repository;

import com.candidate.test.product.entity.WarehouseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *     Given: there is an existing warehouse
 *     When: a product is added to that warehouse
 *     Then: a record will exist in the database showing
 *     that new product is available from that warehouse
 */
@Repository
public interface WarehouseRepository extends CrudRepository<WarehouseEntity, Long> {

    List<WarehouseEntity> findByName(String name);
}
