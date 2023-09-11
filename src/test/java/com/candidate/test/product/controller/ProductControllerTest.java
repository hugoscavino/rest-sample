package com.candidate.test.product.controller;

import com.candidate.test.product.dto.ProductDTO;
import com.candidate.test.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ProductsController.class})
@WebMvcTest
@ActiveProfiles("test")
public class ProductControllerTest extends BaseControllerTest{

    ObjectMapper objectMapper = new ObjectMapper();
    ProductDTO ONE_DTO = new ProductDTO(1L, "PNAME100", "PDESC", false, 1L);

    String NEW_NAME = "NEW_NAME";
    ProductDTO UPDATED_DTO = new ProductDTO(1L, NEW_NAME, "PDESC", false, 1L);

    List<ProductDTO> list = Arrays.asList(ONE_DTO);

    @MockBean
    private ProductService service;

    @Test
    void updateName() throws Exception {
        Mockito.when(service.updateName(anyLong(), anyString())).thenReturn(UPDATED_DTO);

        // Update NAME
        String json = objectMapper.writeValueAsString(UPDATED_DTO);
        ResultActions actions = mockMvc.perform(
                put("/api/v1/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        String contentAsString = actions.andReturn().getResponse().getContentAsString();

        System.out.println("PUT contentAsString ; " + contentAsString);
        actions.andExpect(
                        status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.name", CoreMatchers.is(NEW_NAME)));

    }

    @Test
    void getProductsByWarehouseId() throws Exception {
        Mockito.when(service.findByWarehouseId(any())).thenReturn(list);

        String PNAME = "PNAME2000";
        Long WAREHOUSE_ID = 1L;
        ProductDTO dto = new ProductDTO(0L, PNAME, "Product Description", false, WAREHOUSE_ID);
        String json = objectMapper.writeValueAsString(dto);
        ResultActions actions = mockMvc.perform(
                post("/api/v1/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        String contentAsString = actions.andReturn().getResponse().getContentAsString();
        System.out.println("POST contentAsString ; " + contentAsString);
        actions.andExpect(
                        status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.name", CoreMatchers.is(PNAME)));

        // Search
        actions = mockMvc.perform(
                get("/api/v1/products")
                        .param("warehouse", WAREHOUSE_ID.toString())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        contentAsString = actions.andReturn().getResponse().getContentAsString();

        System.out.println("GET contentAsString ; " + contentAsString);
        actions.andExpect(
                status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body", hasSize(1))
                );

    }
    @Test
    void findByNameLike() throws Exception {
        Mockito.when(service.findByNameLike(any())).thenReturn(list);

        String NAME_WILDCARD = "PNAME";
        String json = objectMapper.writeValueAsString(ONE_DTO);

        // Search
        ResultActions actions = mockMvc.perform(
                get("/api/v1/productsFilter")
                        .param("name", NAME_WILDCARD)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        String contentAsString = actions.andReturn().getResponse().getContentAsString();

        System.out.println("GET contentAsString ; " + contentAsString);
        actions.andExpect(
                        status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body", hasSize(1))
                );

    }

    @Test
    void deleteProduct() throws Exception {
        Mockito.when(service.delete(any())).thenReturn(ONE_DTO);
        String json = objectMapper.writeValueAsString(ONE_DTO);
        // Search
        ResultActions actions = mockMvc.perform(
                delete("/api/v1/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        String contentAsString = actions.andReturn().getResponse().getContentAsString();

        System.out.println("DELETE contentAsString ; " + contentAsString);
        actions.andExpect(
                status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.name", CoreMatchers.is(ONE_DTO.getName()))
                );

    }

}
