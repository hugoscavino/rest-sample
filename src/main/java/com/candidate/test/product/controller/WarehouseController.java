package com.candidate.test.product.controller;

import com.candidate.test.product.dto.WarehouseDTO;
import com.candidate.test.product.service.WarehouseService;
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

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value="/api/v1")
@Tag(name = "Warehouse", description = "The Warehouse API")
public class WarehouseController{
    private final static Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    /**
     * 1 - Add product to warehouse
     *     Given: there is an existing warehouse
     *     When: a product is added to that warehouse
     *     Then: a record will exist in the database showing that new product is available from that warehouse
     *     <br>
     *     NOTE: The name must be unique also otherwise we get confused and would not know where the product was
     *     housed
     *<br>
     * @param warehouse A Warehouse DTO with a unique name
     * @return Warehouse DTO with the ID populated
     */
    @Operation(
            summary = "Save a Warehouse",
            description = "Saves one Warehouse with a unique name. It would be confusing to have a warehouse " +
                    "with the same name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/warehouse")
    @ResponseBody
    public ResponseEntity<Object> save(@NotNull @Valid @RequestBody WarehouseDTO warehouse) {
        final Long savedId = service.save(warehouse);
        warehouse.setId(savedId);
        logger.info("Saved : " + warehouse);
        BaseResponse<WarehouseDTO> body = new BaseResponse<>(warehouse);
        body.msg = "Warehouse saved with id : " + savedId;
        return body.toResponseEntity();

    }

}
