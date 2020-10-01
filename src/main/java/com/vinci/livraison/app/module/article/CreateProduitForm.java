package com.vinci.livraison.app.module.article;

import lombok.Getter;

import java.util.Set;

@Getter
public class CreateProduitForm {

    Long idRestaurateur;
    String titre;
    Double prix;

    Set<Long> categories;
}
