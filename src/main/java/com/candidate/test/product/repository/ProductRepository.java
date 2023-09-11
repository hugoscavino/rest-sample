package com.candidate.test.product.repository;

import com.candidate.test.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    /**
     * 5 - Mechanism to retrieve products filtered given by wildcard name criteria
     *     Given: there are several products with different names
     *     When: a call is made to filter those products based on their name
     *     Then: a filtered list is returned giving back
     *     only those products matching the filter criteria
     * @param name Attribute to use as a criteria/filter
     * @return List<ProductEntity> ProductEntity objects representing one to many rows
     * from the products table
     */
    List<ProductEntity> findByName(String name);

    /**
     * 4 - Mechanism to retrieve products filtered by a warehouse
     *<br>
     *     Given: there are several products in the database
     *     When: a call is made to filter those products by one of their properties
     *     Then: a filtered list is returned giving back only those products matching the filter criteria
     *
     * @param warehouseId id of the warehouse to use to filter
     * @return List<ProductEntity> that match the criteria
     */

    @Query(value="select id, name, description, is_in_stock, warehouse_id FROM product.product p \n" +
            "LEFT OUTER JOIN product.product_warehouse as pw ON pw.product_id = p.id WHERE pw.warehouse_id = :warehouseId", nativeQuery = true)
    List<ProductEntity> findAllProductsByWarehouseId(@Param(value = "warehouseId") Long warehouseId);

    /**
     * see <a href="https://www.baeldung.com/spring-data-partial-update">...</a>
     * see <a href="https://stackoverflow.com/questions/28168520/update-via-modifying-query-in-spring-data-not-working">...</a> for why
     * I added the (clearAutomatically = true) to the annotation
     * @param id Product ID
     * @param name Product Name to change
     */
    @Modifying(clearAutomatically = true)
    @Query("update ProductEntity p set p.name = :name where p.id = :id")
    void updateName(@Param(value = "id") Long id, @Param(value = "name") String name);

    /**
     * 5 - Mechanism to retrieve products filtered given by wildcard name criteria
     * <br>
     *     Given: there are several products with different names
     *     When: a call is made to filter those products based on their name
     *     Then: a filtered list is returned giving back only those products matching the filter criteria
     *
     * @param name with wildcards on both ends
     * @return List<ProductEntity> List of 0..n ProductEntity rows
     */
    @Query("FROM ProductEntity p WHERE p.name LIKE %:name%")
    List<ProductEntity> findByNameLike(@Param("name") String name);



}
