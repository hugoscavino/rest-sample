package com.candidate.test.product.service;

import com.candidate.test.product.dto.ProductDTO;
import com.candidate.test.product.entity.ProductEntity;
import com.candidate.test.product.entity.WarehouseEntity;
import com.candidate.test.product.entity.builder.ProductEntityBuilder;
import com.candidate.test.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <h1>Supporting Stories</h1>
 * <br>
 * <br>
 * <br><strong>1 - Add product to warehouse</strong><br>
 *     Given: there is an existing warehouse
 *     When: a product is added to that warehouse
 *     Then: a record will exist in the database showing that new product is available from that warehouse
 * <br>
 * <br>
 * <strong>2 - Update product name</strong><br>
 *     Given: there is an existing product
 *     When: a product name is changed
 *     Then: the new name will be updated in the database
 * <br>
 * <br>
 * <strong>3 - Transfer product from one warehouse to another</strong><br>
 *     Given: there are two unique warehouses
 *     When: a product is transferred from one warehouse to another
 *     Then: the product will no longer be found in the original warehouse,
 *   	        and it will only be in the warehouse it was transferred to
 * <br>
 * <br>
 * <strong>4 - Mechanism to retrieve products filtered by a warehouse</strong><br>
 *     Given: there are several products in the database
 *     When: a call is made to filter those products by one of their properties
 *     Then: a filtered list is returned giving back only those products matching the filter criteria
 * <br>
 * <br>
 * <strong>5 - Mechanism to retrieve products filtered given by wildcard name criteria</strong><br>
 *     Given: there are several products with different names
 *     When: a call is made to filter those products based on their name
 *     Then: a filtered list is returned giving back only those products matching the filter criteria
 * <br>
 * <br>
 * <strong>6 - Delete a product</strong><br>
 *     Given: a product in the database
 *     When: a call is made to delete the product
 *     Then: the product is no longer available to be retrieved in the system
 * <br>
 * <br>
 */
@Service
public class ProductService {

    /**
     * Default to <code>DEFAULT_WAREHOUSE</code> (0L) Warehouse if one is not assigned.
     */
    public final static Long DEFAULT_WAREHOUSE = 0L;
    public final static Long BAD_PRODUCT_ID = -1L;
    private final ProductRepository repository;

    private final static Logger logger = LoggerFactory.getLogger(ProductService.class);

    /**
     * The repository is mandatory, so we have it here in the ctor and not autowired where we would have the impression
     * that the repo is optional.
     * @param repository The JPA enabled ProductRepository
     */
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Update or save the ProductDTO. Use same service to perform an INSERT or UPDATE
     * Use this service to change the Warehouse Location.
     *<br>
     * If the Warehouse ID is null will assume it to be DEFAULT_WAREHOUSE (0L)
     *
     * @param dto The ProductDTO. Saves will not have an assigned ID. UPDATE will use the ID
     * @return Long PK of the saved object. This will be the newly created ID from a sequence for an INSERT
     */
    public Long save(ProductDTO dto){
        final Long wareHouseId = dto.getWarehouseId();
        logger.info("Attempting to save " + dto + " to warehouse " + wareHouseId);
        if (dto.getWarehouseId() == null){
            dto.setWarehouseId(DEFAULT_WAREHOUSE);
            logger.info("Provided Warehouse is empty setting id to " + DEFAULT_WAREHOUSE);
        }

        final ProductEntity saved = repository.save(toEntity(dto));
        final Long id = saved.getId();
        logger.info("Saved Product with ID " + id + " and with warehouse id : " + saved.getWarehouse().getId());

        return id;
    }

    /**
     * Find a Product using the PK. Only return one Product
     * @param id The PK to use for the query
     * @return Optional<ProductDTO>
     */
    public Optional<ProductDTO> find(Long id) {
        logger.info("Searching for Product with ID " + id);

        final Optional<ProductEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            logger.info("find Product with ID " + id);
            return Optional.of(toDto(optional.get()));
        } else {
            logger.info("find for Product with ID " + id + " did not return any rows");
            return  Optional.empty();
        }

    }

    /**
     * Find all the Products like this name. Collector.toList() will return an empty List for us.
     * @param name - Do not add the wildcard characters, the service will do that
     * @return List<ProductDTO> All the Products (0.n) that meet this criteria
     */
    public List<ProductDTO> findByNameLike(String name) {
        logger.info("Searching for Products with name " + name);

        final List<ProductEntity> products = repository.findByNameLike(name);
        logger.info("findByNameLike Found " + products.size());

        return products
                .stream()
                .map(ProductService::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Find all the Products in this Warehouse. Collector.toList() will return an empty List for us.
     * @param warehouseId ID for Warehouse
     * @return List<ProductDTO> All the Products (0.n) that meet this criteria
     */
    public List<ProductDTO> findByWarehouseId(Long warehouseId) {
        logger.info("Searching for Products with Warehouse ID " + warehouseId);

        final List<ProductEntity> products = repository.findAllProductsByWarehouseId(warehouseId);
        logger.info("findByWarehouseId Found " + products.size() + " for Products for Warehouse ID " + warehouseId);
        // Assigning the warehouseId outside JPA is a WART. The Join Table cannot be referenced
        // from the @Query annotation so I need to add it back to the ProductEntity object
        return products
               .stream()
                .map(pe -> ProductService.toDto(pe, warehouseId))
                .collect(Collectors.toList());

    }

    /**
     * 6 - Delete a product
     * <br>
     *     Given: a product in the database
     *     When: a call is made to delete the product
     *     Then: the product is no longer available to be retrieved in the system
     */
    public ProductDTO delete(Long id) {
        logger.info("Deleting Product with ID " + id);
        Optional<ProductDTO> optional = find(id);
        if (optional.isPresent()){
            ProductDTO dto = optional.get();
            final ProductEntity entity = new ProductEntityBuilder().id(dto.getId()).build();
            repository.delete(entity);
            logger.info("Deleted Product with ID " + id);
            return dto;
        } else {
            logger.warn("Product with ID " + id + " not found. Nothing deleted. Returning a mock DTO");
            return new ProductDTO(BAD_PRODUCT_ID);
        }


    }

    /**
     * Update the name of Product. Note decided to use @Transaction as the underlying
     * JPA and postgreSQL threw an exception without it.
     *
     * @param id   Product ID
     * @param name New Name of the Product
     * @return The updated ProductDTO.
     */
    @Transactional
    public ProductDTO updateName(Long id, String name) {
        logger.info("Updating Product (ID : " + id + ") with new name of " + name);
        repository.updateName(id, name);
        Optional<ProductEntity> optional = repository.findById(id);
        if (optional.isPresent()){
            return toDto(optional.get());
        } else {
            logger.info("Could not find Product ID : " + id);
            return new ProductDTO(BAD_PRODUCT_ID);
        }
    }


    /**
     * We could use MapStruct but want to get to MVP this weekend
     * @param pe a ProductEntity to convert
     * @return ProductDTO. Check the Warehouse if not assigned then set object to <code>DEFAULT_WAREHOUSE</code>
     */
    public static ProductDTO toDto(ProductEntity pe){

        Long warehouseId;
        if (pe.getWarehouse() != null){
            warehouseId = pe.getWarehouse().getId();
        } else {
            warehouseId = DEFAULT_WAREHOUSE;
        }
        return new ProductDTO(pe.getId(),
                pe.getName(),
                pe.getDescription(),
                pe.getInStock(),
                warehouseId);
    }

    public static ProductDTO toDto(ProductEntity pe, Long warehouseId){

        return new ProductDTO(pe.getId(),
                pe.getName(),
                pe.getDescription(),
                pe.getInStock(),
                warehouseId);
    }

    /**
     * Convert a ProductDTO to an ProductEntity
     * @param dto The ProductDTO to convert
     * @return A converted ProductEntity
     */
    public static ProductEntity toEntity(ProductDTO dto){

        return new ProductEntityBuilder()
                .name(dto.getName())
                .description(dto.getDescription())
                .warehouse(new WarehouseEntity(dto.getWarehouseId())).build();
    }

}
