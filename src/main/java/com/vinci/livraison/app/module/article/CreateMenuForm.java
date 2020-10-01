package com.vinci.livraison.app.module.article;

import lombok.Getter;

import java.util.Set;

@Getter
public class CreateMenuForm {

    Long idRestaurateur;
    String titre;
    Double prix;

    Set<Long> categories;
    Set<Long> produits;
}
