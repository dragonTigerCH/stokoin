package com.bsc.stokoin.common.exception.category;

import com.bsc.stokoin.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CategoryException extends ApplicationException {
    protected CategoryException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
