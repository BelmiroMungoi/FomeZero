package com.bbm.fomezero.exception.handler;

import com.bbm.fomezero.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<StandardErrorResponse.ValidationError> validationErrors = new ArrayList<>();

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) objectError).getField();
            String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            validationErrors.add(new StandardErrorResponse.ValidationError(name, msg));
        }

        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(status.value());
        errorResponse.setStatus(status);
        errorResponse.setTitle("Some fields are invalid. Please check your input and try again.");
        errorResponse.setTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        errorResponse.setPath(request.getContextPath());
        errorResponse.setFields(validationErrors);
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return getObjectResponseEntity(request, status, ex.getMessage(), ex);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return getObjectResponseEntity(request, status, ex.getMessage(), ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getObjectResponseEntity(request, status, ex.getMessage(), ex);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        return getObjectResponseEntity(request, status, ex.getMessage(), ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getObjectResponseEntity(request, status, "Your login details are incorrect. Please try again.", ex);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        return getObjectResponseEntity(request, status, ex.getMessage(), ex);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getObjectResponseEntity(request, status, "Your session has expired. Please log in again.", ex);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleDisabledException(DisabledException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getObjectResponseEntity(request, status, "Account disabled. " +
                "Please complete the required steps to activate your account. " +
                "If this persist please contact the Admin", ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ex.printStackTrace();

        return getObjectResponseEntity(request, status, "We’re sorry, but an unexpected error occurred. Please try again.", ex);
    }

    private ResponseEntity<Object> getObjectResponseEntity(HttpServletRequest request, HttpStatus status, String message, Exception ex) {
        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(status.value());
        errorResponse.setStatus(status);
        errorResponse.setTitle(message);
        errorResponse.setTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, status);
    }

}
