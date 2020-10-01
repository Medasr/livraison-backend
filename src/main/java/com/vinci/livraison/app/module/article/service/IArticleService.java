package com.vinci.livraison.app.module.article.service;

import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IArticleService {

    Page<Article> findArticleByIdAndRestaurateur(Long idArticle , Restaurateur restaurateur, Pageable pageable);

    Page<Article> findArticlesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Page<Article> findArticlesByCategorie(Categorie categorie, Pageable pageable);

    Optional<Article> findArticleByIdAndRestaurateur(Long id,Categorie categorie);

    Article loadArticleCategories(Article article);

    Article addCategorieToArticle(Article article,Categorie categorie);

    void removeCategorieFromArticle(Article article,Categorie categorie);





}
