package com.candidate.test.product.controller;

import com.candidate.test.product.dto.ProductDTO;
import com.candidate.test.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
 **/
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value="/api/v1")
@Tag(name = "Product Controller", description = "The Product API")
public class ProductsController extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final ProductService service;

    public ProductsController(ProductService service) {
        this.service = service;
    }
    @Operation(
            summary = "Find One Product by Product Id",
            description = "Find Product with the associated id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get one product operation"),
            @ApiResponse(responseCode = "404", description = "No product found by this id")
    })
    @GetMapping(value = "/product/{id}")
    @ResponseBody
    public ResponseEntity<Object> find(@PathVariable("id") Long id) {
        Optional<ProductDTO> optional = service.find(id);

        BaseResponse<ProductDTO> responseBody;
        if (optional.isPresent()){
            responseBody = new BaseResponse<>(optional.get());
            responseBody.msg = "Product [" + optional.get().getWarehouseId() + "] found";
            return responseBody.toResponseEntity();
        } else {
            responseBody = new BaseResponse<>(new ProductDTO(id));
            responseBody.msg = "Product was not found for id :" + id;
            return responseBody.toBadRequestResponseEntity();
        }

    }

    @Operation(
            summary = "Delete One Product by Product Id",
            description = "Delete Product with id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully deleted one product"),
            @ApiResponse(responseCode = "404", description = "No product found with this id")
    })
    @DeleteMapping(value = "/product")
    @ResponseBody
    public ResponseEntity<Object> delete(@NotNull @Valid @RequestBody ProductDTO dto) {

        logger.info("Deleting : " + dto.getName() + " with id : " + dto.getId());

        ProductDTO deletedDto = service.delete(dto.getId());
        BaseResponse<ProductDTO> responseBody = new BaseResponse<>(deletedDto);
        if (deletedDto.getId().equals(ProductService.BAD_PRODUCT_ID)){
            responseBody.msg = "Product not deleted when searching for " + dto.getId();
            return responseBody.toBadRequestResponseEntity();
        } else {
            responseBody.msg = "Product found and deleted " + deletedDto;
            return responseBody.toResponseEntity();
        }

    }


    /**
     * 1 - Add product to warehouse
     *<br>
     *     Given: there is an existing warehouse
     *     When: a product is added to that warehouse
     *     Then: a record will exist in the database showing that new product is available from that warehouse
     *
     * @see com.candidate.test.product.controller.BaseResponse for the JSON returned to caller
     * @param dto Product DTO with a WareHouse ID
     * @return ResponseEntity with BaseResponse defining
     */
    @Operation(
            summary = "Save One Product",
            description = "Saves one Product with an associated warehouse id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful save product operation")
    })
    @PostMapping("/product")
    @ResponseBody
    public ResponseEntity<Object> save(@NotNull @Valid @RequestBody ProductDTO dto) {
        final Long savedId = service.save(dto);

        dto.setId(savedId);
        logger.info("Saved : " + dto.getName() + " with id : " + dto.getId());
        BaseResponse<ProductDTO> responseBody = new BaseResponse<>(dto);
        responseBody.msg = "Product saved at warehouse " + dto.getWarehouseId();
        return responseBody.toResponseEntity();

    }

    /**
     * 2 - Update product name
     *<br>
     *     Given: there is an existing product
     *     When: a product name is changed
     *     Then: the new name will be updated in the database
     *
     * @see com.candidate.test.product.controller.BaseResponse for the JSON returned to caller
     * @param dto Product DTO with an existing Product ID
     * @return ResponseEntity with BaseResponse defining the action
     */
    @Operation(
            summary = "Update the Name of One Product",
            description = "Updates the name of one Product given the Product Id. Other attributes are ignored")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful put product operation for name")
    })
    @PutMapping("/product")
    @ResponseBody
    public ResponseEntity<Object> updateName(@NotNull @Valid @RequestBody ProductDTO dto) {
        logger.info("Updated Name to  : " + dto.getName() + " with id : " + dto.getId());

        ProductDTO updatedDto = service.updateName(dto.getId(), dto.getName());
        BaseResponse<ProductDTO> responseBody = new BaseResponse<>(updatedDto);
        responseBody.msg = "Product name updated at warehouse " + updatedDto.getId();
        return responseBody.toResponseEntity();

    }

    /**
     * 4 - Mechanism to retrieve products filtered by a warehouse
     *<br>
     *     Given: there are several products in the database
     *     When: a call is made to filter those products by one of their properties
     *     Then: a filtered list is returned giving back only those products matching the filter criteria
     */
    @Operation(
            summary = "Find Products by Warehouse Id",
            description = "Find Products with the fk of warehouse id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get products operation"),
            @ApiResponse(responseCode = "404", description = "No products found by this warehouse id")
    })
    @GetMapping(value = "/products")
    @ResponseBody
    public ResponseEntity<Object> findByWarehouseId(@RequestParam(name = "warehouse") Long warehouse) {
        logger.info("findByWarehouseId searching for products with warehouse id : " + warehouse);

        List<ProductDTO> productDTOList = service.findByWarehouseId(warehouse);
        BaseResponse<List<ProductDTO>> responseBody;
        if (productDTOList != null){
            responseBody = new BaseResponse<>(productDTOList);
            responseBody.msg = "Found " + productDTOList.size() + " Products stored at " + warehouse;
            logger.info(responseBody.msg );
            return responseBody.toResponseEntity();
        } else {
            responseBody = new BaseResponse<>(List.of());
            responseBody.msg = "No Products were not found for warehouse :" + warehouse;
            return responseBody.toBadRequestResponseEntity();
        }
    }


    /**
     * <strong>5 - Mechanism to retrieve products filtered given by wildcard name criteria</strong><br>
     *     Given: there are several products with different names
     *     When: a call is made to filter those products based on their name
     *     Then: a filtered list is returned giving back only those products matching the filter criteria
     * <br>
     * @param name Product Name to use as wildcard
     * @return ResponseEntity<Object>
     */
    @Operation(
            summary = "Find Products by name wildcard",
            description = "Find Products by a name wildcard in both directions, do not add wildcards to string")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get products operation"),
            @ApiResponse(responseCode = "404", description = "No products found by this warehouse id")
    })
    @GetMapping(value = "/productsFilter")
    @ResponseBody
    public ResponseEntity<Object> findByNameLike(@RequestParam(name = "name") String name) {
        logger.info("findByNameLike searching for products with name : " + name);
        List<ProductDTO> productDTOList = service.findByNameLike(name);
        int count = productDTOList.size();

        logger.info("findByNameLike found " + count + " products");

        BaseResponse<List<ProductDTO>> responseBody;
        if (!productDTOList.isEmpty()){
            responseBody = new BaseResponse<>(productDTOList);
            responseBody.msg = "findByNameLike found [" + count + "] rows for the wildcard [%" + name + "%]";
            logger.info(responseBody.msg );
            return responseBody.toResponseEntity();
        } else {
            responseBody = new BaseResponse<>(List.of());
            responseBody.msg = "No Products were not found for wildcard : " + name;
            return responseBody.toBadRequestResponseEntity();
        }

    }
}
