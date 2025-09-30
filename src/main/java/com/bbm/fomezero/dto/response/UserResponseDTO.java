package com.bbm.fomezero.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private boolean enabled;
    private ProfileResponseDTO profile;
    private List<AddressResponseDTO> addresses;
}
