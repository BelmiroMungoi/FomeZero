package com.bbm.fomezero.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private boolean status;
}
