package com.vinci.livraison.app.module.client;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateClientForm {

    @NotBlank(message = "nom est requis")
    String nom;
    @NotBlank(message = "login est requis")
    @Email(message = "login invalide")
    String login;

    @NotBlank(message = "Mot de passe est requis")
    String password;

    String tel;
    String adresse;

}
