package com.vinci.livraison.app.security.userdetails.impl;

import com.vinci.livraison.app.security.authentications.UserType;
import com.vinci.livraison.app.security.userdetails.AbstractUserDetails;

import java.util.Set;

public class RestaurateurDetails extends AbstractUserDetails {

    boolean enseigne;

    Long enseigneId;

    public RestaurateurDetails(Long id, String name, String login, String password, boolean isEnseigne, Long enseigneId, Set<String> autorities, boolean enabled) {
        super(id, name, login, password, autorities, enabled);
        this.enseigne = isEnseigne;
        this.enseigneId = enseigneId;
    }

    public boolean isEnseigne() {
        return enseigne;
    }

    public Long getEnseigneId() {
        return enseigneId;
    }

    @Override
    public UserType getType() {
        return UserType.RESTAURATEUR;
    }
}
