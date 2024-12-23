package com.gl.parcauto.service.impl;

import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.entity.Vacation;
import com.gl.parcauto.entity.Vehicle;
import com.gl.parcauto.exception.AppException;
import com.gl.parcauto.exception.ErrorConstants;
import com.gl.parcauto.repository.DriverRepository;
import com.gl.parcauto.repository.VacationRepository;
import com.gl.parcauto.repository.VehicleRepository;
import com.gl.parcauto.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final VacationRepository vacationRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * Check if driver is available between the specified start and end dates.
     * @param driverId the ID of the driver to check
     * @param start the start date of the trip
     * @param end the end date of the trip
     * @return true if the driver is available, false otherwise
     * @throws AppException if the start or end date is invalid
     */

    @Override
    public boolean isAvailableDriver(String driverId, LocalDateTime start, LocalDateTime end) {
        validateDate(start, end);

        List<Driver> availableDrivers = driverRepository.findDriversWithoutTripsBetweenDates(start, end);
        Vacation currentVacation = vacationRepository.findFirstByDriverOrderByDescId(driverId).orElse(null);

        boolean notInVacation = checkVacationNotInTrip(currentVacation, start, end);
        boolean notInTrip = checkDriverNotInTrip(driverId, availableDrivers);

        return notInVacation && notInTrip;
    }

    /**
     * Check if vehicle is available between the specified start and end dates.
     * @param vehicleId the ID of the vehicle to check
     * @param start the start date of the trip
     * @param end the end date of the trip
     * @return true if the vehicle is available, false otherwise
     * @throws AppException if the start or end date is invalid
     */

    @Override
    public boolean isAvailableVehicle(String vehicleId, LocalDateTime start, LocalDateTime end) {
        validateDate(start, end);

        List<Vehicle> availableVehicles = vehicleRepository.findVehiclesWithoutTripsBetweenDates(start, end);

        boolean notInTrip = checkVehicleNotInTrip(vehicleId, availableVehicles);

        return notInTrip;
    }

    private boolean checkVacationNotInTrip(Vacation currentVacation, LocalDateTime start, LocalDateTime end) {
        if(currentVacation == null)
            return true;
        return currentVacation.getEnd().isBefore(start) || currentVacation.getStart().isAfter(end);
    }

    private boolean checkDriverNotInTrip(String driverId, List<Driver> availableDrivers) {
        return availableDrivers.stream().map(Driver::getCin).anyMatch(d -> d.equals(driverId));
    }

    private boolean checkVehicleNotInTrip(String vehicleId, List<Vehicle> availableVehicles) {
        return availableVehicles.stream().map(Vehicle::getLicensePlate).anyMatch(vehicleId::equals);
    }

    private void validateDate(LocalDateTime start, LocalDateTime end) {
        if(start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now()) || start.isAfter(end)) {
            throw new AppException(HttpStatus.BAD_REQUEST, ErrorConstants.INVALID_DATE);
        }
    }
}
