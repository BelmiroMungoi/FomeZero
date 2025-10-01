package com.bbm.fomezero.service.impl;

import com.bbm.fomezero.dto.request.RestaurantRequestDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.exception.BadRequestException;
import com.bbm.fomezero.exception.ConflictException;
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
    public Restaurant getRestaurantById(Long id) {
        return null;
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return List.of();
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        return List.of();
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
    public AppResponse deleteRestaurant(Long id) {
        return null;
    }
}
