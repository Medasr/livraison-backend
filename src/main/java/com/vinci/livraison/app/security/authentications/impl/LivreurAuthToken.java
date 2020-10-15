package com.vinci.livraison.app.security.authentications.impl;

import com.vinci.livraison.app.security.authentications.AbstractAuthToken;
import com.vinci.livraison.app.security.userdetails.impl.LivreurDetails;

public class LivreurAuthToken extends AbstractAuthToken<LivreurDetails> {

    public LivreurAuthToken(Long id) {
        super(id);
    }

    public LivreurAuthToken(String login, String password) {
        super(login, password);
    }

    public LivreurAuthToken(LivreurDetails principal) {
        super(principal);
    }
}
