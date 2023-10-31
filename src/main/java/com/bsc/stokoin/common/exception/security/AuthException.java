package com.bsc.stokoin.common.exception.security;

import com.bsc.stokoin.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class AuthException extends ApplicationException {

    protected AuthException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }

    protected AuthException(String errorCode, HttpStatus httpStatus, String message,Throwable cause) {
        super(errorCode, httpStatus, message,cause);
    }
}
