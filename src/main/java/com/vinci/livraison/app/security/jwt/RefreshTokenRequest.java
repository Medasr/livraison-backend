package com.vinci.livraison.app.security.jwt;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RefreshTokenRequest {

    @NotBlank(message = "refreshToken est requis")
    String refreshToken;
}
