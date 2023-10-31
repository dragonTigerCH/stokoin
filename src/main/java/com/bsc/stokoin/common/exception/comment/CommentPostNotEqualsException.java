package com.bsc.stokoin.common.exception.comment;

import org.springframework.http.HttpStatus;

public class CommentPostNotEqualsException extends CommentException{
    private static final String MESSAGE = "해당 댓글이 작성된 게시글이 아닙니다.";
    private static final String CODE = "COMMENT-402";

    public CommentPostNotEqualsException() {
        super(CODE, HttpStatus.BAD_REQUEST ,MESSAGE);
    }
}
