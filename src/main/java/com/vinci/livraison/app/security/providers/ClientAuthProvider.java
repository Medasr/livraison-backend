package com.vinci.livraison.app.security.providers;


import com.vinci.livraison.app.security.authentications.impl.ClientAuthToken;
import com.vinci.livraison.app.security.service.impl.ClientAuthService;
import com.vinci.livraison.app.security.userdetails.impl.ClientDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class ClientAuthProvider implements AuthenticationProvider {

    ClientAuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if ((authentication.getPrincipal() instanceof ClientDetails)) {
            return authentication;
        }

        if ((authentication.getPrincipal()) instanceof Long){
            Long id = (Long) authentication.getPrincipal();
            ClientDetails clientDetails = authService.loadUserById(id);
            return new ClientAuthToken(clientDetails);
        }

        String login = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        ClientDetails clientDetails = authService.loadUserByUsername(login);

        if (!authService.isPasswordValid(clientDetails, password)) {
            throw new BadCredentialsException("mot de passe incorrect");
        }

        return new ClientAuthToken(clientDetails);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ClientAuthToken.class);
    }
}
