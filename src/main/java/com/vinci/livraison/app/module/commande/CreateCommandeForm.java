package com.vinci.livraison.app.module.commande;


import lombok.Getter;

import java.util.Set;

@Getter
public class CreateCommandeForm {

    Long idClient;
    Long idRestaurateur;
    String commentaire;

    Set<LigneCommande> lignesCommande;

    @Getter
    public static class LigneCommande {

        Long idArticle;
        Byte quantite;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LigneCommande that = (LigneCommande) o;

            return idArticle != null ? idArticle.equals(that.idArticle) : that.idArticle == null;
        }

        @Override
        public int hashCode() {
            return idArticle != null ? idArticle.hashCode() : 0;
        }
    }
}
