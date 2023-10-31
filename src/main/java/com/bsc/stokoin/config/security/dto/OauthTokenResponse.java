package com.bsc.stokoin.config.security.dto;

import lombok.Data;

@Data
public class OauthTokenResponse {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
    private String error;
    private String error_description;
    private String refresh_token_expires_in;
    private String scope;


}
