package com.gl.parcauto.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.parcauto.dto.request.VehicleInsuranceDtoRequest;
import com.gl.parcauto.dto.response.VehicleInsuranceDtoResponse;
import com.gl.parcauto.entity.InsuranceDuration;
import com.gl.parcauto.service.VehicleInsuranceService;

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

@ContextConfiguration(classes = {VehicleInsuranceController.class})
@ExtendWith(SpringExtension.class)
class VehicleInsuranceControllerDiffblueTest {
    @Autowired
    private VehicleInsuranceController vehicleInsuranceController;

    @MockBean
    private VehicleInsuranceService vehicleInsuranceService;

    /**
     * Method under test:
     * {@link VehicleInsuranceController#createInsurance(String, VehicleInsuranceDtoRequest)}
     */
    @Test
    void testCreateInsurance() throws Exception {
        when(vehicleInsuranceService.getInsurancesForVehicle(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .get("/api/v1/vehicles/{licensePlate}/insurances", "License Plate")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new VehicleInsuranceDtoRequest(null, InsuranceDuration.MONTH)));
        MockMvcBuilders.standaloneSetup(vehicleInsuranceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:
     * {@link VehicleInsuranceController#getInsurancesForVehicle(String)}
     */
    @Test
    void testGetInsurancesForVehicle() throws Exception {
        when(vehicleInsuranceService.getInsurancesForVehicle(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/vehicles/{licensePlate}/insurances", "License Plate");
        MockMvcBuilders.standaloneSetup(vehicleInsuranceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:
     * {@link VehicleInsuranceController#deleteInsurance(String, Long)}
     */
    @Test
    void testDeleteInsurance() throws Exception {
        doNothing().when(vehicleInsuranceService).delete(Mockito.<String>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/vehicles/{licensePlate}/insurances/{insuranceId}", "License Plate", 1L);
        MockMvcBuilders.standaloneSetup(vehicleInsuranceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Vehicle insurance deleted successfully"));
    }

    /**
     * Method under test:
     * {@link VehicleInsuranceController#deleteInsurance(String, Long)}
     */
    @Test
    void testDeleteInsurance2() throws Exception {
        doNothing().when(vehicleInsuranceService).delete(Mockito.<String>any(), Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/vehicles/{licensePlate}/insurances/{insuranceId}", "License Plate", 1L);
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(vehicleInsuranceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Vehicle insurance deleted successfully"));
    }

    /**
     * Method under test:
     * {@link VehicleInsuranceController#getInsuranceById(String, Long)}
     */
    @Test
    void testGetInsuranceById() throws Exception {
        when(vehicleInsuranceService.getById(Mockito.<String>any(), Mockito.<Long>any()))
                .thenReturn(new VehicleInsuranceDtoResponse(1L, LocalDate.of(1970, 1, 1), InsuranceDuration.MONTH));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/vehicles/{licensePlate}/insurances/{insuranceId}", "License Plate", 1L);
        MockMvcBuilders.standaloneSetup(vehicleInsuranceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"activatedDate\":[1970,1,1],\"duration\":\"MONTH\"}"));
    }
}
