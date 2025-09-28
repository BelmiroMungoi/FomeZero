package com.bbm.fomezero.service;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.DriverResponseDTO;
import com.bbm.fomezero.model.Driver;

import java.util.List;

public interface DriverService {

    AppResponse registerDriver(UserRequest userRequest);

    List<DriverResponseDTO> getAllDrivers();

    DriverResponseDTO getDriverById(Long id);
}
