package com.bbm.fomezero.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantResponseDTO {

    private Long id;
    private boolean open;
    private String name;
    private String description;
    private String cuisineType;
    private String contactInfo;
    private String openingHours;
    private UserResponseDTO owner;
    private AddressResponseDTO address;
    private List<String> images;

}
