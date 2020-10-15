package com.vinci.livraison.app.security.authentications.impl;

import com.vinci.livraison.app.security.authentications.AbstractAuthToken;
import com.vinci.livraison.app.security.userdetails.impl.EnseigneDetails;

public class EnseigneAuthToken extends AbstractAuthToken<EnseigneDetails> {

    public EnseigneAuthToken(Long id) {
        super(id);
    }

    public EnseigneAuthToken(EnseigneDetails principal) {
        super(principal);
    }
}
