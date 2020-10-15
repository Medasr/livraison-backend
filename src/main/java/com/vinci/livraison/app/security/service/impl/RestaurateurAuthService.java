package com.vinci.livraison.app.security.service.impl;

import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.restaurateur.repository.RestaurateurRepository;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.service.AuthServiceInterface;
import com.vinci.livraison.app.security.userdetails.impl.RestaurateurDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class RestaurateurAuthService implements AuthServiceInterface<RestaurateurDetails, Restaurateur> {

    RestaurateurRepository restaurateur$;
    PasswordEncoder encoder;

    @Override
    public RestaurateurDetails loadUserById(Long id) throws UserNotFoundException {
        return restaurateur$.findById(id)
                .map(this::map)
                .orElseThrow(() -> new UserNotFoundException("User non trouver"));
    }

    @Override
    public RestaurateurDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return restaurateur$.findByRestaurateurUserLogin(s)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User non trouver"));
    }

    @Override
    public boolean isPasswordValid(RestaurateurDetails user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public RestaurateurDetails map(Restaurateur restaurateur) {

        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_RESTAURATEUR");
        if (restaurateur.isIsenseigne()) {
            authorities.add("ROLE_ENSEIGNE");
        }


        return new RestaurateurDetails(
                restaurateur.getId(),
                restaurateur.getNom(),
                restaurateur.getRestaurateurUser().getLogin(),
                restaurateur.getRestaurateurUser().getPassword(),
                restaurateur.isIsenseigne(),
                restaurateur.getEnseigne().getId(),
                authorities,
                !restaurateur.isShutDown());
    }
}
