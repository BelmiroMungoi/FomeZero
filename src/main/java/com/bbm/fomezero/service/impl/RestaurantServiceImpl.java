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
    @Transactional
    public AppResponse updateRestaurant(Long id, RestaurantRequestDTO restaurantRequest) {
        var restaurant = getRestaurantById(id);
        restaurant.setName(restaurantRequest.getName());
        restaurant.setDescription(restaurantRequest.getDescription());
        restaurant.setCuisineType(restaurantRequest.getCuisineType());
        restaurant.setContactInfo(restaurantRequest.getContactInfo());
        restaurant.setOpeningHours(restaurantRequest.getOpeningHours());
        restaurant.setAddress(addressService.updateAddressAndReturnEntity(id, restaurantRequest.getAddress()));
        var updatedRestaurant = restaurantRepository.save(restaurant);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("Restaurant updated successfully.")
                .description("Welcome, " + updatedRestaurant.getName() + "!")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public AppResponse openOrCloseRestaurant(Long id) {
        var restaurant = getRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("Restaurant status has changed.")
                .description("Open: " + restaurant.isOpen())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public AppResponse approveRestaurant(Long id) {
        var restaurant = getRestaurantById(id);
        restaurant.setApproved(true);
        var approvedRestaurant =restaurantRepository.save(restaurant);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("Restaurant approved .")
                .description("Welcome, " + approvedRestaurant.getName() + "!")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    @Transactional
    public AppResponse deleteRestaurant(Long id) {
        var restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);

        return AppResponse.builder()
                .responseCode(OK.value())
                .responseStatus(OK)
                .responseMessage("Restaurant approved .")
                .description("Goodbye, " + restaurant.getName() + "!")
                .createdAt(LocalDateTime.now())
                .build();
    }
}