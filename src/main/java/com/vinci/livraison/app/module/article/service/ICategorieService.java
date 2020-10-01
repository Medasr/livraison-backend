package com.vinci.livraison.app.module.article.service;

import com.vinci.livraison.app.module.article.CreateCategorieForm;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;

public interface ICategorieService {

    Categorie createCategorie(@Nullable Categorie nullableCategorieMere , CreateCategorieForm form);

    Page<Categorie> findCategoriesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Page<Categorie> findCategoriesByCategoriesMere(Categorie categorie, Pageable pageable);

    Categorie updateCategorie(Categorie categorie, String nouveauTitre);
}
