package com.bbm.fomezero.service;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;

public interface UserService {

   AppResponse createUser(UserRequest userRequest);
}
