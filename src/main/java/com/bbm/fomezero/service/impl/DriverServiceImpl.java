package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.exception.ConflictException;
import com.bbm.fomezero.model.Driver;
import com.bbm.fomezero.repository.DriverRepository;
import com.bbm.fomezero.service.DriverService;
import com.bbm.fomezero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final UserService userService;
    private final DriverRepository driverRepository;

    @Override
    @Transactional
    public AppResponse registerDriver(UserRequest userRequest) {
        if (driverRepository.existsByLicenseNumber(userRequest.getLicenseNumber())) {
            throw new ConflictException("License number already in use!");
        }

        if (!userRequest.getRole().equalsIgnoreCase("DRIVER")) {
            userRequest.setRole("DRIVER"); // Force the role to DRIVER
        }

        var user = userService.createUserAndReturnEntity(userRequest);

        Driver driver = new Driver();
        driver.setUser(user);
        driver.setAvailable(false);
        driver.setLicenseNumber(userRequest.getLicenseNumber());
        driverRepository.save(driver);

        return AppResponse.builder()
                .responseCode(CREATED.value())
                .responseStatus(CREATED)
                .responseMessage("Driver account created successfully.")
                .description("Welcome, " + user.getFullName() + "!")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
