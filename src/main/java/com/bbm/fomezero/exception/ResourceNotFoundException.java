package com.bbm.fomezero.exception;

public class ResourceNotFoundException extends BadRequestException{

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
