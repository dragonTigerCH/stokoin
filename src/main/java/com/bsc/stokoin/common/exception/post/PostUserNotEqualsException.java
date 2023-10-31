package com.bsc.stokoin.common.exception.post;

import org.springframework.http.HttpStatus;

public class PostUserNotEqualsException extends PostException{
    private static final String MESSAGE = "게시글 작성자가 아닙니다.";
    private static final String CODE = "POST-401";

    public PostUserNotEqualsException() {
        super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
    }
}
