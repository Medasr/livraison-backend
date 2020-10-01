package com.vinci.livraison.app.module.enseigne;

import lombok.Getter;

import java.util.Set;

@Getter
public class CreateEnseigneForm {

    private String nom, nomContact, email;
    private byte nbrMaxRestaurateur;
    private Restaurateur restaurateur;


    @Getter
    public static class Restaurateur {
        private String nom, adresse, login, password;
        private Long idVille;
        private Set<Long> types;
    }
}
