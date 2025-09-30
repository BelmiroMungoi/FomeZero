package com.bbm.fomezero.service;

import com.bbm.fomezero.dto.request.AddressRequestDTO;
import com.bbm.fomezero.dto.response.AddressResponseDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.model.Address;

import java.util.List;

public interface AddressService {

    AppResponse createAddress(AddressRequestDTO addressRequest, Long userId);

    Address getAddress(Long id);

    List<AddressResponseDTO> getAllAddressesByUser(Long userId);

    AppResponse updateAddress(Long id, AddressRequestDTO addressRequest);
}
