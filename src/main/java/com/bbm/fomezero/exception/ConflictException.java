package com.bbm.fomezero.exception;

public class ConflictException extends BadRequestException{

    public ConflictException(String msg) {
        super(msg);
    }
}
