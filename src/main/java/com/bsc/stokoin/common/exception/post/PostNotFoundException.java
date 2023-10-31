package com.bsc.stokoin.common.exception.post;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends PostException {
    private static final String MESSAGE = "존재하지 않는 게시글입니다.";
    private static final String CODE = "POST-400";

    public PostNotFoundException() {
        super(CODE, HttpStatus.BAD_REQUEST ,MESSAGE);
    }
}
