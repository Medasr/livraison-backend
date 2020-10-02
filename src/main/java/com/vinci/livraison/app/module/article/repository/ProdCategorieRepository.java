package com.vinci.livraison.app.module.article.repository;


import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.article.entity.ProdCat;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProdCategorieRepository extends JpaRepository<ProdCat, Long> {

    @EntityGraph(attributePaths = {"categorie.restaurateur"})
    List<ProdCat> findAllByArticle(Article article);

    // article belong to restaurateur
    boolean existsByArticleAndCategorieRestaurateur(Article article, Restaurateur restaurateur);

    // Prod Categorie Existe
    boolean existsByArticleAndCategorie(Article article, Categorie categorie);


    //
    Optional<ProdCat> findByArticleAndCategorie(Article article, Categorie categorie);


    long countByArticle(Article article);
}
