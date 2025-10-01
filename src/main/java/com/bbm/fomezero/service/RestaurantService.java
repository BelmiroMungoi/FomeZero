package com.bbm.fomezero.service;

import com.bbm.fomezero.dto.request.RestaurantRequestDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.model.Restaurant;
import com.bbm.fomezero.model.User;

import java.util.List;

public interface RestaurantService {

    AppResponse createRestaurant(Long userId, RestaurantRequestDTO restaurantRequest);

    Restaurant getRestaurantById(Long id);

    Restaurant getRestaurantByUserId(Long userId);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> searchRestaurants(String keyword);

    AppResponse updateRestaurant(Long id, RestaurantRequestDTO restaurantRequest);

    AppResponse openOrCloseRestaurant(Long id);

    AppResponse deleteRestaurant(Long id);
}
