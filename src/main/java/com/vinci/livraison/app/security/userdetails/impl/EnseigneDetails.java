package com.vinci.livraison.app.security.userdetails.impl;

import com.vinci.livraison.app.security.authentications.UserType;
import com.vinci.livraison.app.security.userdetails.AbstractUserDetails;

import java.util.Set;

public class EnseigneDetails extends AbstractUserDetails {

    public EnseigneDetails(Long id, String name,String email, Set<String> autorities, boolean enabled) {
        super(id, name, email, null, autorities, enabled);
    }

    @Override
    public UserType getType() {
        return UserType.ENSEIGNE;
    }
}
