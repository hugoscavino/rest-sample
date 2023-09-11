package com.candidate.test.product.controller;

import com.candidate.test.product.dto.ProductDTO;
import com.candidate.test.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {OperationsController.class})
@WebMvcTest
@ActiveProfiles("test")
public class OperationsControllerTest extends BaseControllerTest{

    private ObjectMapper objectMapper = new ObjectMapper();
    Long WAREHOUSE_ID = 1L;
    ProductDTO dto = new ProductDTO(1L, "PNAME100", "PDESC", false, WAREHOUSE_ID);

    @MockBean
    private ProductService service;

    @Test
    void updateWarehouse() throws Exception {
        String PNAME = "PNAME1000";

        // Update WAREHOUSE ID
        Long WAREHOUSE_ID = 200L;
        dto.setWarehouseId(WAREHOUSE_ID);
        String json = objectMapper.writeValueAsString(dto);
        ResultActions actions = mockMvc.perform(
                put("/api/v1/operations/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        String contentAsString = actions.andReturn().getResponse().getContentAsString();

        System.out.println("PUT contentAsString ; " + contentAsString);
        actions.andExpect(
                        status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.warehouseId", CoreMatchers.is(200)));
    }
}
