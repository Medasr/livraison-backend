package com.vinci.livraison.app.security.providers;

import com.vinci.livraison.app.security.authentications.impl.RestaurateurAuthToken;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.service.impl.RestaurateurAuthService;
import com.vinci.livraison.app.security.userdetails.impl.RestaurateurDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RestaurateurAuthProvider implements AuthenticationProvider {

    RestaurateurAuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if ((authentication.getPrincipal() instanceof RestaurateurDetails)) {
            return authentication;
        }

        if(authentication.getPrincipal() instanceof Long){
            Long id = (Long) authentication.getPrincipal();
            RestaurateurDetails restaurateurDetails = authService.loadUserById(id);

            if (!restaurateurDetails.isEnabled()) {
                throw new DisabledException("Compte désactivé");
            }

            return new RestaurateurAuthToken(restaurateurDetails);
        }

        if(authentication.getPrincipal() instanceof String){

            String login = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();

            RestaurateurDetails restaurateurDetails = authService.loadUserByUsername(login);

            if (!authService.isPasswordValid(restaurateurDetails, password)) {
                throw new BadCredentialsException("mot de passe incorrect");
            }

            if (!restaurateurDetails.isEnabled()) {
                throw new DisabledException("Compte désactivé");
            }

            return new RestaurateurAuthToken(restaurateurDetails);
        }

        throw new UserNotFoundException("User non trouver");

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(RestaurateurAuthToken.class);
    }
}
