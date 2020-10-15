package com.vinci.livraison.app.module.commande;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientCommandeForm extends CreateCommandeForm{

    Long idRestaurateur;
    String commentaire;
}
