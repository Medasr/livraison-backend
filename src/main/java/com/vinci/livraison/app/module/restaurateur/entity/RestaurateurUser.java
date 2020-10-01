package com.vinci.livraison.app.module.restaurateur.entity;

import com.vinci.livraison.app.module.shared.User;

import javax.persistence.Entity;

@Entity
public class RestaurateurUser extends User {

    private static final long serialVersionUID = -2108420605300863958L;

    public RestaurateurUser() {
        super();
    }

    public RestaurateurUser(String login, String password) {
        super(login, password);
    }

}
