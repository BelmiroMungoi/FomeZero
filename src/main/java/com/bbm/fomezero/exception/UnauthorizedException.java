package com.bbm.fomezero.exception;

public class UnauthorizedException extends BadRequestException{

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
