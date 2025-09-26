package com.bbm.fomezero.service;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.model.Driver;

public interface DriverService {

    AppResponse registerDriver(UserRequest userRequest);
}
