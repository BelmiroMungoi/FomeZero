package com.bbm.fomezero.controller;

import com.bbm.fomezero.dto.request.RestaurantRequestDTO;
import com.bbm.fomezero.dto.response.AppResponse;
import com.bbm.fomezero.dto.response.RestaurantResponseDTO;
import com.bbm.fomezero.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponseDTO>> searchRestaurants(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(restaurantService.searchRestaurants(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> findRestaurantById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(restaurantService.findRestaurantById(id));
    }

    @GetMapping("/owner")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantByOwnerId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantByOwnerId(id));
    }
}
