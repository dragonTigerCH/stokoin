package com.bsc.stokoin.user.domain.enums;

import lombok.Getter;

@Getter
public enum Gender {

    MAN("남자"),
    WOMEN("여자");

    private String description;

    Gender(String description) {
        this.description = description;
    }
}
