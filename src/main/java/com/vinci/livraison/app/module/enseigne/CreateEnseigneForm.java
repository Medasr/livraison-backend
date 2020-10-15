package com.vinci.livraison.app.module.enseigne;

import com.vinci.livraison.app.module.restaurateur.CreateRestaurateurForm;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreateEnseigneForm {

    private String nom, nomContact, email;
    private byte nbrMaxRestaurateur;
    private CreateRestaurateurForm restaurateur;

}
