package com.gl.parcauto.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.parcauto.dto.request.GrisCardDtoRequest;
import com.gl.parcauto.dto.response.GrisCardDtoResponse;
import com.gl.parcauto.service.GrisCardService;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {GrisCardController.class})
@ExtendWith(SpringExtension.class)
class GrisCardControllerDiffblueTest {
    @Autowired
    private GrisCardController grisCardController;

    @MockBean
    private GrisCardService grisCardService;

    /**
     * Method under test:
     * {@link GrisCardController#createGrisCard(String, GrisCardDtoRequest)}
     */
    @Test
    void testCreateGrisCard() throws Exception {
        when(grisCardService.getGrisCardsForVehicle(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .get("/api/v1/vehicles/{licensePlate}/grisCards", "License Plate")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new GrisCardDtoRequest(null)));
        MockMvcBuilders.standaloneSetup(grisCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link GrisCardController#getGrisCardsForVehicle(String)}
     */
    @Test
    void testGetGrisCardsForVehicle() throws Exception {
        when(grisCardService.getGrisCardsForVehicle(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/vehicles/{licensePlate}/grisCards", "License Plate");
        MockMvcBuilders.standaloneSetup(grisCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link GrisCardController#deleteGrisCard(String, Long)}
     */
    @Test
    void testDeleteGrisCard() throws Exception {
        doNothing().when(grisCardService).delete(Mockito.<String>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/vehicles/{licensePlate}/grisCards/{grisCardId}", "License Plate", 1L);
        MockMvcBuilders.standaloneSetup(grisCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Gris card deleted successfully"));
    }

    /**
     * Method under test: {@link GrisCardController#deleteGrisCard(String, Long)}
     */
    @Test
    void testDeleteGrisCard2() throws Exception {
        doNothing().when(grisCardService).delete(Mockito.<String>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/vehicles/{licensePlate}/grisCards/{grisCardId}", "License Plate", 1L);
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(grisCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Gris card deleted successfully"));
    }

    /**
     * Method under test: {@link GrisCardController#getGrisCardById(String, Long)}
     */
    @Test
    void testGetGrisCardById() throws Exception {
        when(grisCardService.getById(Mockito.<String>any(), Mockito.<Long>any()))
                .thenReturn(new GrisCardDtoResponse(1L, LocalDate.of(1970, 1, 1)));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/vehicles/{licensePlate}/grisCards/{grisCardId}", "License Plate", 1L);
        MockMvcBuilders.standaloneSetup(grisCardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"activatedDate\":[1970,1,1]}"));
    }
}
