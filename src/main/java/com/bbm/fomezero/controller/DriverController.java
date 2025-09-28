package com.bbm.fomezero.controller;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.DriverResponseDTO;
import com.bbm.fomezero.model.User;
import com.bbm.fomezero.service.DriverService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/drivers")
@Tag(name = "3. Driver")
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/")
    public ResponseEntity<AppResponse> registerDriver(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(driverService.registerDriver(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    @GetMapping("/profile")
    public ResponseEntity<DriverResponseDTO> getDriverById(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(driverService.getDriverById(user.getDriver().getId()));
    }
}
