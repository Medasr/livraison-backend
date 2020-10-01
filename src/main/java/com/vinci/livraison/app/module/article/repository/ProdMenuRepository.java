package com.vinci.livraison.app.module.article.repository;

import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.article.entity.ProdCat;
import com.vinci.livraison.app.module.article.entity.ProdMenu;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdMenuRepository extends JpaRepository<ProdMenu,Long> {

    @EntityGraph(attributePaths = {"produit"})
    List<ProdMenu> findAllByMenu(Article menu);

    // article belong to restaurateur
    boolean existsByMenuAndProduit(Article menu, Article produit);

    Optional<ProdMenu> findByMenuAndProduit(Article menu, Article produit);

    long countByMenu(Article menu);

}
