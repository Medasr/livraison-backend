package com.vinci.livraison.app.module.article.service.impl;


import com.vinci.livraison.app.module.article.CreateCategorieForm;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.article.repository.CategorieRepository;
import com.vinci.livraison.app.module.article.service.ICategorieService;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CategorieService implements ICategorieService {

    CategorieRepository categorie$;

    @Override
    public Categorie createCategorie(Restaurateur restaurateur,Categorie nullableCategorieMere, CreateCategorieForm form) {
        Categorie categorie = new Categorie();
        categorie.setCategorieMere(nullableCategorieMere);
        categorie.setTitre(form.getTitre());
        categorie.setRestaurateur(restaurateur);
        return categorie$.save(categorie);
    }

    @Override
    public Page<Categorie> findCategoriesByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return categorie$.findCategoriesByRestaurateur(restaurateur, pageable);
    }

    @Override
    public List<Categorie> findCategoriesByCategoriesMere(Categorie categorie) {
        return categorie$.findCategoriesByCategorieMere(categorie);
    }

    @Override
    public Categorie updateCategorie(Categorie categorie, String nouveauTitre) {
        categorie.setTitre(nouveauTitre);
        return categorie$.save(categorie);
    }

    @Override
    public Optional<Categorie> findCategorieByIdAndRestaurateur(long id, Restaurateur restaurateur) {
        return categorie$.findCategorieByIdAndRestaurateur(id, restaurateur);
    }
}
