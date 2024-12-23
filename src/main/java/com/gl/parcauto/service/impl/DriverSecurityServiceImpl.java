package com.gl.parcauto.service.impl;

import com.gl.parcauto.auth.IAuthenticationFacade;
import com.gl.parcauto.entity.Driver;
import com.gl.parcauto.repository.DriverRepository;
import com.gl.parcauto.service.DriverSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverSecurityServiceImpl implements DriverSecurityService {
    private final DriverRepository driverRepository;
    private final IAuthenticationFacade authenticationFacade;
    @Override
    public boolean isDriver(String cin) {
        String username = authenticationFacade.getAuthentication().getName();

        // Get driver from database
        Optional<Driver> optionalDriver = driverRepository.findById(cin);

        if(optionalDriver.isEmpty())
            return false;
        Driver driver = optionalDriver.get();

        return driver.getUser() != null && driver.getUser().getUsername().equals(username);
    }
}
