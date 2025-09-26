package com.bbm.fomezero.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    private String fullName;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String licenseNumber;
}
