package com.vinci.livraison.app.module.client;


import lombok.Getter;

import javax.persistence.Column;

@Getter
public class CreateClientForm {

    String nom;
    String login;
    String password;

    String tel;
    String adresse;

}
