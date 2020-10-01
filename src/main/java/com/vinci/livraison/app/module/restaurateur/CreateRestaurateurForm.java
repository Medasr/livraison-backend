package com.vinci.livraison.app.module.restaurateur;

import lombok.Getter;

import java.util.Set;

@Getter
public class CreateRestaurateurForm {

    private String nom,adresse,login,password;
    private Long idVille;
    private Set<Long> types;

}
