package com.bbm.fomezero.service;

import com.bbm.fomezero.dto.request.UserRequest;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.UserResponseDTO;
import com.bbm.fomezero.model.User;

import java.util.List;

public interface UserService {

    AppResponse createUser(UserRequest userRequest);

    User createUserAndReturnEntity(UserRequest userRequest);

    List<UserResponseDTO> getAllUsers();

    User getUser(Long id);

    UserResponseDTO getUserById(Long id);

    AppResponse updateUser(Long id, UserRequest userRequest);
}


