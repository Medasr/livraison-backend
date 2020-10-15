package com.vinci.livraison.app.security.service.impl;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.client.repository.ClientRepository;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.service.AuthServiceInterface;
import com.vinci.livraison.app.security.userdetails.impl.ClientDetails;
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
public class ClientAuthService implements AuthServiceInterface<ClientDetails, Client> {

    ClientRepository client$;
    PasswordEncoder encoder;


    @Override
    public ClientDetails loadUserById(Long id) throws UserNotFoundException {
        return client$.findById(id)
                .map(this::map)
                .orElseThrow(() -> new UserNotFoundException("User non trouver"));
    }

    @Override
    public ClientDetails loadUserByUsername(String login) {
        return client$.findByLogin(login)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User non trouver"));
    }

    @Override
    public boolean isPasswordValid(ClientDetails user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public ClientDetails map(Client client) {

        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_CLIENT");

        return new ClientDetails(
                client.getId(),
                client.getNom(),
                client.getLogin(),
                client.getPassword(),
                authorities,
                true
        );
    }


}
