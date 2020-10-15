package com.vinci.livraison.app.security.userdetails.impl;

import com.vinci.livraison.app.security.authentications.UserType;
import com.vinci.livraison.app.security.userdetails.AbstractUserDetails;

import java.util.Set;

public class ClientDetails extends AbstractUserDetails {

    boolean hasAddress;

    public ClientDetails(Long id, String name, String login, String password, Set<String> autorities, boolean enabled) {
        super(id, name, login, password, autorities, enabled);
    }

    public boolean isHasAddress() {
        return hasAddress;
    }

    @Override
    public UserType getType() {
        return UserType.CLIENT;
    }


}
