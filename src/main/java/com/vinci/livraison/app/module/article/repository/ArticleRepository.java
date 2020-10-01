package com.vinci.livraison.app.module.article.repository;

import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT distinct Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE Article.id = ?1 AND pc.categorie.restaurateur = ?2")
    Optional<Article> findArticleByIdAndRestaurateur(long id, Restaurateur restaurateur);

    @Query("SELECT DISTINCT Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE pc.categorie.restaurateur = ?1")
    Page<Article> findArticlesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    @Query("SELECT Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE pc.categorie = ?1")
    Page<Article> findArticlesByCategorie(Categorie categorie, Pageable pageable);

    @Query("SELECT distinct Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE Article.id = ?1  AND Article.menu = false AND pc.categorie.restaurateur = ?2")
    Optional<Article> findProduitByIdAndRestaurateur(long id, Restaurateur restaurateur);

    @Query("SELECT DISTINCT Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE Article.menu = false AND pc.categorie.restaurateur = ?1")
    Page<Article> findProduitsByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    @Query("SELECT distinct Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE Article.id = ?1 AND Article.menu = true AND pc.categorie.restaurateur = ?2")
    Optional<Article> findMenuByIdAndRestaurateur(long id, Restaurateur restaurateur);

    @Query("SELECT DISTINCT Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE Article.menu = true AND pc.categorie.restaurateur = ?1")
    Page<Article> findMenusByRestaurateur(Restaurateur restaurateur, Pageable pageable);


    @Query("SELECT Produit FROM ProdMenu pm LEFT JOIN pm.produit Produit WHERE pm.menu = ?1")
    List<Article> findProduitsByMenu(Article menu);

    @Query("SELECT distinct Article FROM ProdCat pc LEFT JOIN pc.article Article WHERE Article.id in ?1 AND pc.categorie.restaurateur = ?2")
    List<Article> findProduitsByIdInRestaurateur(Set<Long> produits, Restaurateur restaurateur);
}
