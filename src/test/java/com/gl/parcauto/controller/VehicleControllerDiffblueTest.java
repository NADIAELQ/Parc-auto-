package com.gl.parcauto.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.parcauto.dto.request.VehicleDtoRequest;
import com.gl.parcauto.dto.response.GrisCardDtoResponse;
import com.gl.parcauto.dto.response.VehicleAllFieldsDtoResponse;
import com.gl.parcauto.dto.response.VehicleDtoResponse;
import com.gl.parcauto.dto.response.VehicleInsuranceDtoResponse;
import com.gl.parcauto.dto.response.VignetteDtoResponse;
import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.entity.InsuranceDuration;
import com.gl.parcauto.service.VehicleService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {VehicleController.class})
@ExtendWith(SpringExtension.class)
class VehicleControllerDiffblueTest {
    @Autowired
    private VehicleController vehicleController;

    @MockBean
    private VehicleService vehicleService;

    /**
     * Method under test: {@link VehicleController#createVehicle(VehicleDtoRequest)}
     */
    @Test
    void testCreateVehicle() throws Exception {
        when(vehicleService.getAllVehicle()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new VehicleDtoRequest("License Plate", 1, DriverLicenseType.A)));
        MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:
     * {@link VehicleController#getAvailableVehiclesBetweenDates(LocalDateTime, LocalDateTime)}
     */
    @Test
    void testGetAvailableVehiclesBetweenDates() throws Exception {
        when(vehicleService.getAvailableVehiclesBetweenDates(Mockito.<LocalDateTime>any(), Mockito.<LocalDateTime>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/vehicles");
        MockHttpServletRequestBuilder paramResult = getResult.param("endDate",
                String.valueOf(LocalDate.of(1970, 1, 1).atStartOfDay()));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("startDate",
                String.valueOf(LocalDate.of(1970, 1, 1).atStartOfDay()));
        MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link VehicleController#deleteVehicle(String)}
     */
    @Test
    void testDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).delete(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/vehicles/{licensePlate}",
                "License Plate");
        MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Vehicle deleted successfully"));
    }

    /**
     * Method under test: {@link VehicleController#deleteVehicle(String)}
     */
    @Test
    void testDeleteVehicle2() throws Exception {
        doNothing().when(vehicleService).delete(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/vehicles/{licensePlate}",
                "License Plate");
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Vehicle deleted successfully"));
    }

    /**
     * Method under test: {@link VehicleController#getAllVehicles()}
     */
    @Test
    void testGetAllVehicles() throws Exception {
        when(vehicleService.getAllVehicle()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/vehicles");
        MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link VehicleController#getVehicleById(String)}
     */
    @Test
    void testGetVehicleById() throws Exception {
        GrisCardDtoResponse grisCard = new GrisCardDtoResponse(1L, LocalDate.of(1970, 1, 1));

        VehicleInsuranceDtoResponse vehicleInsurance = new VehicleInsuranceDtoResponse(1L, LocalDate.of(1970, 1, 1),
                InsuranceDuration.MONTH);

        VignetteDtoResponse vignette = new VignetteDtoResponse(1L, LocalDate.of(1970, 1, 1));

        when(vehicleService.getById(Mockito.<String>any())).thenReturn(new VehicleAllFieldsDtoResponse("License Plate", 1,
                DriverLicenseType.A, grisCard, vehicleInsurance, vignette, new HashSet<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/vehicles/{licensePlate}",
                "License Plate");
        MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"licensePlate\":\"License Plate\",\"year\":1,\"type\":\"A\",\"grisCard\":{\"id\":1,\"activatedDate\":[1970,1,1]},"
                                        + "\"vehicleInsurance\":{\"id\":1,\"activatedDate\":[1970,1,1],\"duration\":\"MONTH\"},\"vignette\":{\"id\":1,\"deliveryDate"
                                        + "\":[1970,1,1]},\"technicalVisits\":[]}"));
    }

    /**
     * Method under test:
     * {@link VehicleController#updateVehicle(String, VehicleDtoRequest)}
     */
    @Test
    void testUpdateVehicle() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .put("/api/v1/vehicles/{licensePlate}", "License Plate")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new VehicleDtoRequest("License Plate", 1, DriverLicenseType.A)));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test:
     * {@link VehicleController#updateVehicle(String, VehicleDtoRequest)}
     */
    @Test
    void testUpdateVehicle2() throws Exception {
        when(vehicleService.update(Mockito.<String>any(), Mockito.<VehicleDtoRequest>any()))
                .thenReturn(new VehicleDtoResponse("License Plate", 1, DriverLicenseType.A));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders
                .put("/api/v1/vehicles/{licensePlate}", "License Plate")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new VehicleDtoRequest("License Plate", 2023, DriverLicenseType.A)));
        MockMvcBuilders.standaloneSetup(vehicleController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"licensePlate\":\"License Plate\",\"year\":1,\"type\":\"A\"}"));
    }
}
