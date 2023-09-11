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

/**
 * <h1>Supporting Stories</h1>
 * <br>
 * <br>
 * <strong>3 - Transfer product from one warehouse to another</strong><br>
 *     Given: there are two unique warehouses
 *     When: a product is transferred from one warehouse to another
 *     Then: the product will no longer be found in the original warehouse,
 *   	        and it will only be in the warehouse it was transferred to
 * <br>
 * <br>
 **/
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value="/api/v1")
@Tag(name = "Operations Controller", description = "Operations Manager API")
public class OperationsController  extends BaseController {
    private final static Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final ProductService service;

    public OperationsController(ProductService service) {
        this.service = service;
    }

    @Operation(
            summary = "Update the Warehouse Location of One Product",
            description = "Updates the warehouse id of one Product given the Product Id and new warehouse id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful patch product operation for name")
    })
    @PutMapping("/operations/product")
    @ResponseBody
    public ResponseEntity<Object> updateWarehouseLocation(@NotNull @Valid @RequestBody ProductDTO dto) {
        logger.info("Update Warehouse Location to  : " + dto.getWarehouseId());
        BaseResponse<ProductDTO> responseBody = new BaseResponse<>(dto);
        service.save(dto);
        responseBody.msg = "Product location updated to be now be warehouse [" + dto.getWarehouseId() + "]";
        return responseBody.toResponseEntity();
    }
}
