package com.vinci.livraison.app.security.authentications.impl;

import com.vinci.livraison.app.security.authentications.AbstractAuthToken;
import com.vinci.livraison.app.security.userdetails.impl.RestaurateurDetails;

public class RestaurateurAuthToken extends AbstractAuthToken<RestaurateurDetails> {

    public RestaurateurAuthToken(Long id) {
        super(id);
    }

    public RestaurateurAuthToken(String login, String password) {
        super(login, password);
    }

    public RestaurateurAuthToken(RestaurateurDetails principal) {
        super(principal);
    }
}
