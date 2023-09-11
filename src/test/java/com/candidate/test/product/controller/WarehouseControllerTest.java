package com.candidate.test.product.controller;

import com.candidate.test.product.dto.WarehouseDTO;
import com.candidate.test.product.service.WarehouseService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {WarehouseController.class})
@WebMvcTest
@ActiveProfiles("test")
public class WarehouseControllerTest extends BaseControllerTest{

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private WarehouseService service;

    @Test
    void saveWarehouse() throws Exception {
        WarehouseDTO dto = new WarehouseDTO("WAREHOUSE12345", "33544");
        String json = objectMapper.writeValueAsString(dto);
        ResultActions actions = mockMvc.perform(
                post("/api/v1/warehouse")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        String contentAsString = actions.andReturn().getResponse().getContentAsString();
        System.out.println("contentAsString ; " + contentAsString);
        actions.andExpect(
                status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.name", CoreMatchers.is("WAREHOUSE12345")))
                .andExpect(jsonPath("$.body.zipCode", CoreMatchers.is("33544")));
    }
}
