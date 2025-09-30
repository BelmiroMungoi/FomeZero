package com.bbm.fomezero.dto.response;

import lombok.Data;

@Data
public class AddressResponseDTO {

    private Long id;
    private String street;
    private String city;
    private String province;
    private String zipCode;
    private Double latitude;
    private Double longitude;
}
