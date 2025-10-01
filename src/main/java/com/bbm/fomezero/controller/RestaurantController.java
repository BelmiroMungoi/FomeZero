package com.bbm.fomezero.controller;

import com.bbm.fomezero.dto.request.RestaurantRequestDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants")
@Tag(name = "3. Restaurant Management")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/")
    public ResponseEntity<AppResponse> createRestaurant(@RequestBody RestaurantRequestDTO restaurantRequest) {
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurantRequest), HttpStatus.CREATED);
    }
}
