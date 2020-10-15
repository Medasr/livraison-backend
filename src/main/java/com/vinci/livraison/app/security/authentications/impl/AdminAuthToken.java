package com.vinci.livraison.app.security.authentications.impl;

import com.vinci.livraison.app.security.authentications.AbstractAuthToken;
import com.vinci.livraison.app.security.userdetails.impl.AdminDetails;

public class AdminAuthToken extends AbstractAuthToken<AdminDetails> {

    public AdminAuthToken(Long id) {
        super(id);
    }

    public AdminAuthToken(String login, String password) {
        super(login, password);
    }

    public AdminAuthToken(AdminDetails principal) {
        super(principal);
    }
}
