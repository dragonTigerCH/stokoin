package com.bsc.stokoin.common.exception.comment;

import com.bsc.stokoin.common.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CommentException extends ApplicationException {
    protected CommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
