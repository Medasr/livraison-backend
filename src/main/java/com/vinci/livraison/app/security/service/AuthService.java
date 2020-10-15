package com.vinci.livraison.app.security.service;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.security.authentications.AbstractAuthToken;
import com.vinci.livraison.app.security.authentications.AuthTokenFactory;
import com.vinci.livraison.app.security.authentications.UserType;
import com.vinci.livraison.app.security.authentications.impl.EnseigneAuthToken;
import com.vinci.livraison.app.security.jwt.JwtService;
import com.vinci.livraison.app.security.jwt.JwtTokenResponse;
import com.vinci.livraison.app.security.userdetails.AbstractUserDetails;
import com.vinci.livraison.app.security.userdetails.impl.*;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;

    public Optional<AbstractUserDetails> getCurrentUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(authentication -> authentication instanceof AbstractAuthToken)
                .map(Authentication::getPrincipal)
                .filter(o -> o instanceof AbstractUserDetails)
                .map(o -> (AbstractUserDetails) o);
    }

    public JwtTokenResponse authenticate(UserType userType, String login, String password) throws AuthenticationException {

            Authentication authentication = AuthTokenFactory.getAuthentication(userType, login, password);
            authentication = authenticationManager.authenticate(authentication);

            AbstractUserDetails userDetails = ((AbstractUserDetails)authentication.getPrincipal());

            return jwtService.generateTokens(userDetails.getId(),userDetails.getType());

    }


    public JwtTokenResponse authenticateAsEnseigne(Long enseignId) throws AuthenticationException {

        Authentication authentication = AuthTokenFactory.getAuthentication(UserType.ENSEIGNE,enseignId);
        authentication = authenticationManager.authenticate(authentication);
        AbstractUserDetails userDetails = (AbstractUserDetails) authentication.getPrincipal();

        return jwtService.generateTokens(userDetails.getId(), userDetails.getType());
    }
}
