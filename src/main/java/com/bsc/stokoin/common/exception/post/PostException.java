package com.bsc.stokoin.common.exception.post;

import com.bsc.stokoin.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PostException extends ApplicationException {
    protected PostException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
