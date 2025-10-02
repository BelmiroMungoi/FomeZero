package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.dto.request.RestaurantRequestDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.RestaurantResponseDTO;
import com.bbm.fomezero.exception.BadRequestException;
import com.bbm.fomezero.exception.ConflictException;
import com.bbm.fomezero.exception.ResourceNotFoundException;
import com.bbm.fomezero.mapper.EntityConverter;
import com.bbm.fomezero.model.Restaurant;
import com.bbm.fomezero.repository.RestaurantRepository;
import com.bbm.fomezero.service.AddressService;
import com.bbm.fomezero.service.RestaurantService;
import com.bbm.fomezero.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final UserService userService;
    private final AddressService addressService;
    private final RestaurantRepository restaurantRepository;
    private final EntityConverter<Restaurant, RestaurantRequestDTO> entityConverter;
    private final EntityConverter<Restaurant, RestaurantResponseDTO> responseConverter;

    @Override
    @Transactional
    public AppResponse createRestaurant(RestaurantRequestDTO restaurantRequest) {
        var user = userService.getUser(restaurantRequest.getUserId());

        if (user.getRestaurant() != null) {
            throw new BadRequestException("This account already has a restaurant.");
        }

        if (restaurantRepository.existsByName(restaurantRequest.getName())) {
            throw new ConflictException("Restaurant name already exists.");
        }

        if (restaurantRequest.getAddress() == null) {
            throw new BadRequestException("Please enter a valid address.");
        }

        var restaurant = entityConverter.mapDtoToEntity(restaurantRequest, Restaurant.class);
        restaurant.setOwner(user);
        restaurant.setApproved(false);

        var savedRestaurant = restaurantRepository.save(restaurant);
        var address = addressService.createAddressAndReturnEntity(restaurantRequest.getAddress(), user.getId());
        savedRestaurant.setAddress(address);
        userService.deactivateUser(user.getId());

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("Restaurant created successfully.")
                .description("Welcome, " + savedRestaurant.getName() + "!")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Restaurant not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponseDTO findRestaurantById(Long id) {
        return responseConverter.mapEntityToDto(
                getRestaurantById(id), RestaurantResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponseDTO getRestaurantByOwnerId(Long userId) {
        var restaurant = restaurantRepository.findByOwnerId(userId).orElseThrow(() ->
                new ResourceNotFoundException("Restaurant not found."));

        return responseConverter.mapEntityToDto(restaurant, RestaurantResponseDTO.class);
    }

    @Override
    @Transactional
    public List<RestaurantResponseDTO> getAllRestaurants() {
        return responseConverter.mapEntityToDtoList(
                restaurantRepository.findAll(),
                RestaurantResponseDTO.class
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponseDTO> searchRestaurants(String keyword) {
        return responseConverter.mapEntityToDtoList(
                restaurantRepository.searchRestaurant(keyword),
                RestaurantResponseDTO.class
        );
    }

    @Override
    public AppResponse updateRestaurant(Long id, RestaurantRequestDTO restaurantRequest) {
        return null;
    }

    @Override
    public AppResponse openOrCloseRestaurant(Long id) {
        return null;
    }

    @Override
    public AppResponse approveRestaurant(Long id) {
        return null;
    }

    @Override
    public AppResponse deleteRestaurant(Long id) {
        return null;
    }
}
