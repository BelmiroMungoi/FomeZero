package com.bbm.fomezero.dto.request;

import lombok.Data;

@Data
public class AddressRequestDTO {

    private String street;
    private String city;
    private String province;
    private String zipCode;
    private Double latitude;
    private Double longitude;
}
