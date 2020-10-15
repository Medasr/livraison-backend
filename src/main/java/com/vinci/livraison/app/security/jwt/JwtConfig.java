package com.vinci.livraison.app.security.jwt;


import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@ConfigurationProperties("security.jwt")
@Getter
@Setter
public class JwtConfig {

    private String headerName = "Authorization";

    private String headerPrefix = "Bearer ";

    private String tokenSigningKey = "secret-signing-key";

    private Integer accessTokenExpTime = 15;

    private Integer refreshTokenExpTime = 60;

    private SignatureAlgorithm signatureAlgorithm = HS256;

    public String getTokenBase64SigningKey() {
        return Base64.getUrlEncoder().encodeToString(tokenSigningKey.getBytes(StandardCharsets.UTF_8));
    }

    public Integer getAccessTokenExpTime(TimeUnit unit) throws IllegalStateException {
        return minutesToSpecificUnit(accessTokenExpTime, unit);
    }

    public Integer getRefreshTokenExpTime(TimeUnit unit) throws IllegalStateException {

        return minutesToSpecificUnit(refreshTokenExpTime, unit);

    }

    private Integer minutesToSpecificUnit(int minutes, TimeUnit unit) throws IllegalStateException {
        switch (unit) {
            case MILLISECONDS:
                return minutes * 60000;
            case SECONDS:
                return minutes * 60;
            case MINUTES:
                return minutes;
            default:
                throw new IllegalStateException("Unexpected value: " + unit);
        }
    }
}
