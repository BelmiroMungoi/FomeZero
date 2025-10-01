package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.dto.request.AddressRequestDTO;
import com.bbm.fomezero.dto.response.AddressResponseDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.exception.ResourceNotFoundException;
import com.bbm.fomezero.mapper.EntityConverter;
import com.bbm.fomezero.model.Address;
import com.bbm.fomezero.repository.AddressRepository;
import com.bbm.fomezero.service.AddressService;
import com.bbm.fomezero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserService userService;
    private final AddressRepository addressRepository;
    private final EntityConverter<Address, AddressRequestDTO> entityConverter;
    private final EntityConverter<Address, AddressResponseDTO> entityResponseConverter;

    @Override
    @Transactional
    public AppResponse createAddress(AddressRequestDTO addressRequest, Long userId) {
        createAddressAndReturnEntity(addressRequest, userId);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("Address created successfully.")
                .description("Address created!")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public Address createAddressAndReturnEntity(AddressRequestDTO addressRequest, Long userId) {
        var user = userService.getUser(userId);
        var address = entityConverter.mapDtoToEntity(addressRequest, Address.class);
        address.setUser(user);
        addressRepository.save(address);
        return addressRepository.save(address);
    }

    @Override
    @Transactional(readOnly = true)
    public Address getAddress(Long id) {
        return addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Address not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getAllAddressesByUser(Long userId) {
        return entityResponseConverter.mapEntityToDtoList(
                addressRepository.findAllByUserId(userId), AddressResponseDTO.class);
    }

    @Override
    @Transactional
    public AppResponse updateAddress(Long id, AddressRequestDTO addressRequest) {
        var address = getAddress(id);
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setProvince(addressRequest.getProvince());
        address.setZipCode(addressRequest.getZipCode());
        address.setLongitude(addressRequest.getLongitude());
        address.setLatitude(addressRequest.getLatitude());
        addressRepository.save(address);

        return  AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("Address updated successfully.")
                .description("Address updated!")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
