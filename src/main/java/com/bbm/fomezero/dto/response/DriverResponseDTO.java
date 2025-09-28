package com.bbm.fomezero.dto.response;

import lombok.Data;

@Data
public class DriverResponseDTO {

    private Long id;
    private String licenseNumber;
    private boolean available;
    private UserResponseDTO user;
}
