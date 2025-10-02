package com.bbm.fomezero.service;

import com.bbm.fomezero.dto.request.RestaurantRequestDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.RestaurantResponseDTO;
import com.bbm.fomezero.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    AppResponse createRestaurant(RestaurantRequestDTO restaurantRequest);

    Restaurant getRestaurantById(Long id);

    RestaurantResponseDTO findRestaurantById(Long id);

    RestaurantResponseDTO getRestaurantByOwnerId(Long userId);

    List<RestaurantResponseDTO> getAllRestaurants();

    List<RestaurantResponseDTO> searchRestaurants(String keyword);

    AppResponse updateRestaurant(Long id, RestaurantRequestDTO restaurantRequest);

    AppResponse openOrCloseRestaurant(Long id);

    AppResponse approveRestaurant(Long id);

    AppResponse deleteRestaurant(Long id);
}
