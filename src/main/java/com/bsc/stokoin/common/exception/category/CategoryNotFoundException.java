package com.bsc.stokoin.common.exception.category;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends CategoryException{
    private static final String MESSAGE = "존재하지 않는 카테고리입니다.";
    private static final String CODE = "CATEGORY-400";

    public CategoryNotFoundException() {
        super(CODE, HttpStatus.BAD_REQUEST ,MESSAGE);
    }
}
