package com.bbm.fomezero.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class AppResponse {

    private int responseCode;
    private HttpStatus responseStatus;
    private String responseMessage;
    private String description;
    private LocalDateTime createdAt;
}
