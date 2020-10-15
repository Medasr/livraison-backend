package com.vinci.livraison.app.controllers.auth;


import com.vinci.livraison.app.security.jwt.AccessTokenResponse;
import com.vinci.livraison.app.security.jwt.RefreshTokenRequest;
import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import com.vinci.livraison.app.module.shared.exception.EntityNotFoundException;
import com.vinci.livraison.app.security.jwt.JwtService;
import com.vinci.livraison.app.security.jwt.JwtTokenResponse;
import com.vinci.livraison.app.security.jwt.entity.Token;
import com.vinci.livraison.app.security.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class TokenController {

    AuthService authService;
    JwtService jwtService;

    @PostMapping("")
    public ResponseEntity generateToken(@Valid @RequestBody LoginRequest request) {

            JwtTokenResponse resp = authService.authenticate(request.getUserType(), request.getLogin(), request.getPassword());
            return ResponseEntity.ok(resp);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("as-enseigne/{enseigne}")
    public ResponseEntity authenticateAsEnseigne(@PathVariable Enseigne enseigne){

        return Optional.ofNullable(enseigne)
                .map(Enseigne::getId)
                .map(authService::authenticateAsEnseigne)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new EntityNotFoundException("Enseigne introuvable"));

    }

    @PostMapping("tokens/refresh")
    public ResponseEntity refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

        Token token = jwtService.parseRefreshTokenOrfails(refreshTokenRequest.getRefreshToken());
        AccessTokenResponse accessTokenResponse = jwtService.generateNewAccessToken(token);

        return ResponseEntity.ok(accessTokenResponse);

    }

    @DeleteMapping("tokens")
    public ResponseEntity revokeToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {


        Token token = jwtService.parseRefreshTokenOrfails(refreshTokenRequest.getRefreshToken());

        jwtService.revokeToken(token);


        return ResponseEntity.noContent().build();
    }

    @GetMapping("me")
    @PreAuthorize("isAuthenticated()")
    public  ResponseEntity currentUser(Authentication authentication){

        return Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }
}
