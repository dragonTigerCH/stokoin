package com.bsc.stokoin.common.exception.comment;

import org.springframework.http.HttpStatus;

public class CommentUserNotEqualsException extends CommentException{
    private static final String MESSAGE = "댓글 작성자가 아닙니다.";
    private static final String CODE = "COMMENT-401";

    public CommentUserNotEqualsException() {
        super(CODE, HttpStatus.BAD_REQUEST ,MESSAGE);
    }
}
