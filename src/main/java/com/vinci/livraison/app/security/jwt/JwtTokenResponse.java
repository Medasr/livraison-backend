package com.vinci.livraison.app.security.jwt;

import lombok.Value;

@Value
public class JwtTokenResponse {

    String accessToken, refreshToken;
}