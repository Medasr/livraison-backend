package com.vinci.livraison.app.security.userdetails.impl;

import com.vinci.livraison.app.security.authentications.UserType;
import com.vinci.livraison.app.security.userdetails.AbstractUserDetails;

import java.util.Set;


public class LivreurDetails extends AbstractUserDetails {

    private boolean needConfirmation;

    public LivreurDetails(Long id, String name, String login, String password, Set<String> autorities, boolean enabled, boolean needConfirmation) {
        super(id, name, login, password, autorities, enabled);
        this.needConfirmation = needConfirmation;
    }

    public boolean isNeedConfirmation() {
        return needConfirmation;
    }

    @Override
    public UserType getType() {
        return UserType.LIVREUR;
    }
}
