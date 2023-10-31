package com.bsc.stokoin.common.exception.comment;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CommentException{
    private static final String MESSAGE = "존재하지 않는 댓글입니다.";
    private static final String CODE = "COMMENT-400";

    public CommentNotFoundException() {
        super(CODE, HttpStatus.BAD_REQUEST ,MESSAGE);
    }
}
