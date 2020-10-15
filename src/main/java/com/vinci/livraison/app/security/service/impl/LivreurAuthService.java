package com.vinci.livraison.app.security.service.impl;


import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.livreur.repository.LivreurRepository;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.service.AuthServiceInterface;
import com.vinci.livraison.app.security.userdetails.impl.LivreurDetails;
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
public class LivreurAuthService implements AuthServiceInterface<LivreurDetails, Livreur> {

    LivreurRepository livreur$;
    PasswordEncoder encoder;

    @Override
    public LivreurDetails loadUserById(Long id) throws UserNotFoundException {
        return livreur$.findById(id).map(this::map).orElseThrow(() -> new UserNotFoundException("User non trouver"));
    }

    @Override
    public LivreurDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return livreur$.findByLogin(s).map(this::map).orElseThrow(() -> new UsernameNotFoundException("User non trouver"));
    }

    @Override
    public boolean isPasswordValid(LivreurDetails user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public LivreurDetails map(Livreur livreur) {
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_LIVREUR");

        boolean enable = !livreur.getRestaurateur().isShutDown();
        boolean needConfirmation = livreur.getPassword().isEmpty();
        return new LivreurDetails(
                livreur.getId(),
                livreur.getNom(),
                livreur.getLogin(),
                livreur.getPassword(),
                null,
                enable,
                needConfirmation
        );
    }
}
