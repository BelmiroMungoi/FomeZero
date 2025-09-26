package com.bbm.fomezero.exception;

public class ForbiddenException extends BadRequestException{

    public ForbiddenException(String msg) {
        super(msg);
    }
}
