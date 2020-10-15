package com.vinci.livraison.app.security.service;

import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.Serializable;

public interface AuthServiceInterface<T extends UserDetails, E extends Serializable> extends UserDetailsService {

    T loadUserById(Long id) throws UserNotFoundException;

    @Override
    T loadUserByUsername(String s) throws UsernameNotFoundException;

    boolean isPasswordValid(T user, String password);

    T map(E entity);


}
