package com.vinci.livraison.app.module.commande.exception;

public class CommandeCantHaveNoLigneCommande extends RuntimeException {
    public CommandeCantHaveNoLigneCommande(String s) {
        super(s);
    }
}
