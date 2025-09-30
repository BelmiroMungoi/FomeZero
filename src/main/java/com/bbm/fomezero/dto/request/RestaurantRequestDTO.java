package com.bbm.fomezero.dto.request;

import com.bbm.fomezero.dto.response.AddressResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantRequestDTO {

    private String name;
    private String description;
    private String cuisineType;
    private String contactInfo;
    private String openingHours;
    private boolean isOpen;
    private AddressRequestDTO address;
    private List<String> images;
}
