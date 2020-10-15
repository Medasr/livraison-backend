package com.vinci.livraison.app.security.providers;

import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import com.vinci.livraison.app.module.enseigne.service.impl.EnseigneService;
import com.vinci.livraison.app.security.authentications.impl.EnseigneAuthToken;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.userdetails.impl.EnseigneDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class EnseigneAuthProvider implements AuthenticationProvider {

    EnseigneService enseigneService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication.getPrincipal() instanceof Long){

            Long id = (Long) authentication.getPrincipal();
            EnseigneDetails details = enseigneService.getEnseigneById(id)
                    .map(this::convertToUserDetails)
                    .orElseThrow(()->new UserNotFoundException("Enseigne non trouvé" ));

            if( !details.isEnabled()){
                throw new DisabledException("Enseigne est désactivé");
            }

            return new EnseigneAuthToken(details);

        }

        if (authentication.getPrincipal() instanceof EnseigneDetails){
            return authentication;
        }

        throw new UserNotFoundException("Enseigne non trouvé");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(EnseigneAuthToken.class);
    }

    private EnseigneDetails convertToUserDetails(Enseigne enseigne){
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_ENSEIGNE");

        return new EnseigneDetails(
                enseigne.getId(),
                enseigne.getNom(),
                enseigne.getEmailContact(),
                authorities,
                enseigne.isActive()
        );
    }
}
