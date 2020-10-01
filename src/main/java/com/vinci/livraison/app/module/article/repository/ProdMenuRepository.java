package com.vinci.livraison.app.module.article.repository;

import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.ProdMenu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdMenuRepository extends JpaRepository<ProdMenu,Long> {

    @EntityGraph(attributePaths = {"produit"})
    List<ProdMenu> findAllByMenu(Article menu);

}
