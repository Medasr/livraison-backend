package com.vinci.livraison.app.module.article.service;

import com.vinci.livraison.app.module.article.CreateCategorieForm;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface ICategorieService {

    Categorie createCategorie(Restaurateur restaurateur, @Nullable Categorie nullableCategorieMere, CreateCategorieForm form);

    Page<Categorie> findCategoriesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    List<Categorie> findCategoriesByCategoriesMere(Categorie categorie);

    Categorie updateCategorie(Categorie categorie, String nouveauTitre);

    Optional<Categorie> findCategorieByIdAndRestaurateur(long id, Restaurateur restaurateur);
}
