package com.gl.parcauto.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.parcauto.dto.request.DriverVehicleToTripDto;
import com.gl.parcauto.dto.response.TripDtoResponse;
import com.gl.parcauto.entity.DriverLicenseType;
import com.gl.parcauto.service.TripService;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

@ContextConfiguration(classes = {TripController.class})
@ExtendWith(SpringExtension.class)
class TripControllerDiffblueTest {
    @Autowired
    private TripController tripController;

    @MockBean
    private TripService tripService;

    /**
     * Method under test:
     * {@link TripController#assignDriverAndVehicleToTrip(Long, DriverVehicleToTripDto)}
     */
    @Test
    void testAssignDriverAndVehicleToTrip() throws Exception {
        LocalDateTime startDate = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(tripService.assignDriverAndVehicleToTrip(Mockito.<Long>any(), Mockito.<DriverVehicleToTripDto>any()))
                .thenReturn(new TripDtoResponse(1L, "Driver Cin", "Vehicle License Plate", startDate,
                        LocalDate.of(1970, 1, 1).atStartOfDay(), "127.0.0.1", "127.0.0.1", DriverLicenseType.A));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/api/v1/trips/{id}/assign", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new DriverVehicleToTripDto("42", "42")));
        MockMvcBuilders.standaloneSetup(tripController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}
