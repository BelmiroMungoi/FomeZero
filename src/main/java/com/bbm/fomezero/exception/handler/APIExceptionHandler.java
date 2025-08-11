package com.bbm.fomezero.exception.handler;

import com.bbm.fomezero.exception.BadRequestException;
import com.bbm.fomezero.exception.ResourceNotFoundException;
import com.bbm.fomezero.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
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
        errorResponse.setTitle("Erro de validação! Um ou mais campos estão inválidos!");
        errorResponse.setTime(OffsetDateTime.now());
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return getObjectResponseEntity(request, status, "Ocorreu um erro inesperado.", ex);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return getObjectResponseEntity(request, status, ex.getMessage(), ex);
    }

    private ResponseEntity<Object> getObjectResponseEntity(HttpServletRequest request, HttpStatus status, String message, Exception ex) {
        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(status.value());
        errorResponse.setStatus(status);
        errorResponse.setTitle(message);
        errorResponse.setTime(OffsetDateTime.now());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, status);
    }

/*    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(status.value());
        errorResponse.setStatus(status);
        errorResponse.setTitle(ex.getMessage());
        errorResponse.setTime(OffsetDateTime.now());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, status);
    }*/
}
