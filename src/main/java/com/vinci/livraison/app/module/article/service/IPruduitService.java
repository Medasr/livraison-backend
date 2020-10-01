package com.vinci.livraison.app.module.article.service;

import com.vinci.livraison.app.module.article.CreateProduitForm;
import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPruduitService {

    Article createProduit(Restaurateur restaurateur, CreateProduitForm form);

    Optional<Article> findProduitByIdAndRestaurateur(Long idProduit, Restaurateur restaurateur);

    Page<Article> findProduitsByRestaurateur(Restaurateur restaurateur, Pageable pageable);

}
