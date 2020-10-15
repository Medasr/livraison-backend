package com.vinci.livraison.app.security.providers;

import com.vinci.livraison.app.security.authentications.impl.AdminAuthToken;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.service.impl.AdminAuthService;
import com.vinci.livraison.app.security.userdetails.impl.AdminDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@AllArgsConstructor
public class AdminAuthProvider implements AuthenticationProvider {

    AdminAuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if(authentication.getPrincipal() instanceof AdminDetails){
            return authentication;
        }

        if ((authentication.getPrincipal()) instanceof Long) {
            Long id = (Long) authentication.getPrincipal();

            return new AdminAuthToken(authService.loadUserById(id));
        }

        if((authentication.getPrincipal()) instanceof String){

            String login = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();

            AdminDetails adminDetails = authService.loadUserByUsername(login);

            if (!authService.isPasswordValid(adminDetails, password)) {
                throw new BadCredentialsException("mot de passe incorrect");
            }

            return new AdminAuthToken(adminDetails);
        }

        throw new UserNotFoundException("User non trouver");


    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(AdminAuthToken.class);
    }
}
