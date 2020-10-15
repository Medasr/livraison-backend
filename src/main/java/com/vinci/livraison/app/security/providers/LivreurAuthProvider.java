package com.vinci.livraison.app.security.providers;

import com.vinci.livraison.app.security.authentications.impl.LivreurAuthToken;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.service.impl.LivreurAuthService;
import com.vinci.livraison.app.security.userdetails.impl.LivreurDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class LivreurAuthProvider implements AuthenticationProvider {

    LivreurAuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        if ((authentication.getPrincipal() instanceof Long)) {

            Long id = (Long) authentication.getPrincipal();
            LivreurDetails livreurDetails = authService.loadUserById(id);

            if (!livreurDetails.isEnabled()) {
                throw new DisabledException("Compte désactivé");
            }

            return new LivreurAuthToken(livreurDetails);

        }

        if ((authentication.getPrincipal() instanceof String)) {

            String login = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();

            LivreurDetails livreurDetails = authService.loadUserByUsername(login);

            if (!livreurDetails.isNeedConfirmation()) {
                if (!authService.isPasswordValid(livreurDetails, password)) {
                    throw new BadCredentialsException("mot de passe incorrect");
                }
            }

            if (!livreurDetails.isEnabled()) {
                throw new DisabledException("Compte désactivé");
            }


            return new LivreurAuthToken(livreurDetails);
        }


        throw new UserNotFoundException("User non trouver");

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(LivreurAuthToken.class);
    }
}
