package com.vinci.livraison.app.security.authentications.impl;

import com.vinci.livraison.app.security.authentications.AbstractAuthToken;
import com.vinci.livraison.app.security.userdetails.impl.ClientDetails;


public class ClientAuthToken extends AbstractAuthToken<ClientDetails> {

    public ClientAuthToken(Long id) {
        super(id);
    }

    public ClientAuthToken(String login, String password) {
        super(login, password);
    }

    public ClientAuthToken(ClientDetails principal) {
        super(principal);
    }
}
