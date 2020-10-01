package com.vinci.livraison.app.module.article.service;

import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IArticleService {

    Optional<Article> findArticleByIdAndRestaurateur(Long idArticle, Restaurateur restaurateur);

    Page<Article> findArticlesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Page<Article> findArticlesByCategorie(Categorie categorie, Pageable pageable);

    Article loadArticleCategories(Article article);

    Article addCategorieToExistingArticle(Article article, Categorie categorie);

    void removeCategorieFromExistingArticle(Article article, Categorie categorie);


}
