package com.vinci.livraison.app.security.authentications;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractAuthToken<T extends UserDetails> extends UsernamePasswordAuthenticationToken {

    public AbstractAuthToken(Long id) {
        super(id, null);
    }

    public AbstractAuthToken(String login, String password) {
        super(login, password);
    }

    public AbstractAuthToken(T principal) {
        super(principal, null, principal.getAuthorities());
    }
}
