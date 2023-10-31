package com.bsc.stokoin.common.exception.user;

import com.bsc.stokoin.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class UserException extends ApplicationException {

    protected UserException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
