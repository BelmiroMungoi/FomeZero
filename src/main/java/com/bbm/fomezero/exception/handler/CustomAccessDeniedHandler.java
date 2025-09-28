package com.bbm.fomezero.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(HttpStatus.FORBIDDEN.value());
        errorResponse.setStatus(HttpStatus.FORBIDDEN);
        errorResponse.setTitle("Access denied: You do not have permission to access this resource.");
        errorResponse.setTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        errorResponse.setPath(request.getRequestURI());

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}